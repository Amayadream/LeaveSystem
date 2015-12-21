package com.amayadream.leave.activiti.controller.activiti;

import com.amayadream.leave.util.ActivitiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 流程模型控制器
 * 
 * @author henryyan
 */
@Controller
@RequestMapping(value = "/workflow/model")
public class ModelController {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  RepositoryService repositoryService;

  /**
   * 模型列表
   */
  @RequestMapping(value = "list")
  public ModelAndView modelList() {
    ModelAndView mav = new ModelAndView("model-list");
    List<Model> list = repositoryService.createModelQuery().list();
    mav.addObject("list", list);
    return mav;
  }

  /**
   * 创建模型
   */
  @RequestMapping(value = "create", method = RequestMethod.POST)
  public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                     HttpServletRequest request, HttpServletResponse response) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode editorNode = objectMapper.createObjectNode();
      editorNode.put("id", "canvas");
      editorNode.put("resourceId", "canvas");
      ObjectNode stencilSetNode = objectMapper.createObjectNode();
      stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
      editorNode.put("stencilset", stencilSetNode);
      Model modelData = repositoryService.newModel();

      ObjectNode modelObjectNode = objectMapper.createObjectNode();
      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
      description = StringUtils.defaultString(description);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
      modelData.setMetaInfo(modelObjectNode.toString());
      modelData.setName(name);
      modelData.setKey(StringUtils.defaultString(key));

      repositoryService.saveModel(modelData);
      repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

      response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
    } catch (Exception e) {
      logger.error("创建模型失败：", e);
    }
  }

  /**
   * 根据Model部署流程
   */
  @RequestMapping(value = "deploy/{modelId}")
  public String deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
    try {
      Model modelData = repositoryService.getModel(modelId);
      ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
      byte[] bpmnBytes = null;

      BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
      bpmnBytes = new BpmnXMLConverter().convertToXML(model);

      String processName = modelData.getName() + ".bpmn20.xml";
      Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"UTF-8")).deploy();  //这里务必加上utf-8,否则各种乱码,切记切记
      redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "部署失败!");
    }
    return "redirect:/workflow/model/list";
  }

  /**
   * 导出model对象为指定类型
   * @param modelId 模型ID
   * @param type 导出文件类型(bpmn\json)
   */
  @RequestMapping(value = "export/{modelId}/{type}")
  public void export(@PathVariable("modelId") String modelId, @PathVariable("type") String type, HttpServletResponse response) {
    try {
      Model modelData = repositoryService.getModel(modelId);
      BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
      byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

      JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
      BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

      // 处理异常
      if (bpmnModel.getMainProcess() == null) {
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.getOutputStream().println("no main process, can't export for type: " + type);
        response.flushBuffer();
        return;
      }
      String filename = "";
      byte[] exportBytes = null;

      String mainProcessId = bpmnModel.getMainProcess().getId();

      if(type.equals("bpmn")){
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        exportBytes = xmlConverter.convertToXML(bpmnModel);
        filename = mainProcessId + ".bpmn20.xml";
      }else{
        exportBytes = modelEditorSource;
        filename = mainProcessId + ".json";
      }
      ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
      IOUtils.copy(in, response.getOutputStream());

      response.setHeader("Content-Disposition", "attachment; filename=" + filename);
      response.setCharacterEncoding("utf-8");
      response.flushBuffer();
    } catch (Exception e) {
      logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
    }
  }

  /**
   * 解析模型,获取步骤名称,角色以及工具
   * @param modelId
   * @return
   */
  @RequestMapping(value = "getXml/{modelId}")
  public ModelAndView getXml(@PathVariable("modelId") String modelId, ActivitiUtil activitiUtil){
    ModelAndView mav = new ModelAndView("/result");
    Model modelData = repositoryService.getModel(modelId);
    byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
    String xml = activitiUtil.getStringXmlByByte(modelEditorSource);
    if(xml != null){
      List<ActivitiUtil> list = activitiUtil.getInfoByStringXml(xml);
      mav.addObject("result",list);
      mav.addObject("modelId",modelId);
      return mav;
    }else{
      return null;
    }
  }

  /**
   * 修改模型
   * @param modelId
   * @param name
   * @param groups
   * @param tools
   * @param activitiUtil
     */
  @RequestMapping(value = "saveModel")
  public String saveModel(@RequestParam("id") String modelId, @RequestParam String[] name, @RequestParam String[] groups, @RequestParam String[] tools, ActivitiUtil activitiUtil, RedirectAttributes redirectAttributes){
    try {
      Model modelData = repositoryService.getModel(modelId);
      byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
      String xml = activitiUtil.getStringXmlByByte(modelEditorSource);
      String data = activitiUtil.setInfoByStringXml(xml, name, groups, tools);
      XMLInputFactory xif = XMLInputFactory.newInstance();
      BpmnJsonConverter converter = new BpmnJsonConverter();
      ByteArrayInputStream is = new ByteArrayInputStream(data.getBytes("utf-8"));
      InputStreamReader in = new InputStreamReader(is, "utf-8");
      XMLStreamReader xtr = xif.createXMLStreamReader(in);
      BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
      ObjectNode modelNode = converter.convertToJson(bpmnModel);
      repositoryService.addModelEditorSource(modelId, modelNode.toString().getBytes("utf-8"));
      redirectAttributes.addFlashAttribute("message","配置成功");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error","配置失败,请重试~");
    } catch (XMLStreamException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error","配置失败,请重试~");
    }
    return "redirect:/workflow/model/list";
  }


  @RequestMapping(value = "delete/{modelId}")
  public String delete(@PathVariable("modelId") String modelId) {
     repositoryService.deleteModel(modelId);
    return "redirect:/workflow/model/list";
  }

}
