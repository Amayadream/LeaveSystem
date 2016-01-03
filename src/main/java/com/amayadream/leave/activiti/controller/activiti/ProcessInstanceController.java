package com.amayadream.leave.activiti.controller.activiti;

import com.alibaba.fastjson.JSON;
import com.amayadream.leave.activiti.service.leave.LeaveWorkflowService;
import com.amayadream.leave.pojo.Leave;
import com.amayadream.leave.pojo.Log;
import com.amayadream.leave.service.ILogService;
import com.amayadream.leave.util.*;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workflow/processinstance")
@SessionAttributes("userid")
public class ProcessInstanceController {

    @Resource
    private ILogService logService;
    @Resource
    private Log log;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    protected LeaveWorkflowService workflowService;

    /**
     * 查询所有流程
     * @param model     model
     * @param request   request
     * @return
     */
    @RequestMapping(value = "process-list")
    public ModelAndView all(Model model, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/all-process-list");
        Page<ProcessDefinition> page = new Page<ProcessDefinition>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();
        page.setResult(list);
        page.setTotalCount(query.count());
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping(value = "start/{key}")
    public String start(@PathVariable("key") String key, @ModelAttribute("userid") String userid, Leave leave, RedirectAttributes redirectAttributes, DateUtil dateUtil, LogUtil logUtil) {
        try {
            leave.setUserid(userid);
            leave.setApplytime(dateUtil.getDateTime24());
            Map<String, Object> variables = new HashMap<String, Object>();
            ProcessInstance processInstance = workflowService.start(key, leave, variables);
            log.setUserid(userid);
            log.setTime(dateUtil.getDateTime24());
            log.setType(logUtil.LOG_TYPE_START);
            log.setDetail(logUtil.LOG_DETAIL_LEAVE);
            logService.insert(log);
            redirectAttributes.addFlashAttribute("message", "流程已启动，流程ID：" + processInstance.getId());
        }
        catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                redirectAttributes.addFlashAttribute("error", "没有部署流程，请在[工作流]->[流程管理]页面点击<重新部署流程>");
            } else {
                redirectAttributes.addFlashAttribute("error", "系统内部错误！");
            }
        }
        return "redirect:/leave/list/task";
    }

  /**
   * 查询所有运行中的流程
   * @param model
   * @param request
   * @return
     */
  @RequestMapping(value = "running")
  public ModelAndView running(Model model, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("running-manage");
    Page<ProcessInstance> page = new Page<ProcessInstance>(PageUtil.PAGE_SIZE);
    int[] pageParams = PageUtil.init(page, request);

    ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
    List<ProcessInstance> list = processInstanceQuery.listPage(pageParams[0], pageParams[1]);
    page.setResult(list);
    page.setTotalCount(processInstanceQuery.count());
    mav.addObject("page", page);
    return mav;
  }

  /**
   * 挂起、激活流程实例
   * @param state
   * @param processInstanceId
   * @param redirectAttributes
   * @return
   */
  @RequestMapping(value = "update/{state}/{processInstanceId}")
  public String updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId,
                            RedirectAttributes redirectAttributes) {
    if (state.equals("active")) {
      redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processInstanceId + "]的流程实例。");
      runtimeService.activateProcessInstanceById(processInstanceId);
    } else if (state.equals("suspend")) {
      runtimeService.suspendProcessInstanceById(processInstanceId);
      redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processInstanceId + "]的流程实例。");
    }
    return "redirect:/workflow/processinstance/running";
  }
}
