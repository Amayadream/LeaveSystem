package com.amayadream.leave.activiti.controller.leave;

import com.amayadream.leave.activiti.service.leave.LeaveWorkflowService;
import com.amayadream.leave.pojo.Leave;
import com.amayadream.leave.service.ILeaveService;
import com.amayadream.leave.util.Page;
import com.amayadream.leave.util.PageUtil;
import com.amayadream.leave.util.UserUtil;
import com.amayadream.leave.util.Variable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 请假控制器，包含保存、启动流程
 * 
 * @author HenryYan
 */
@Controller
@RequestMapping(value = "leave")
@SessionAttributes("userid")
public class ExperimentController {

  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired protected LeaveWorkflowService workflowService;
  @Autowired protected RuntimeService runtimeService;
  @Autowired protected TaskService taskService;
  @Autowired protected RepositoryService repositoryService;

  @Resource private ILeaveService leaveService;
  @Resource private Leave leave;

    @RequestMapping(value = "deploy")
    public String deploy(RedirectAttributes redirectAttributes){
        repositoryService.createDeployment().name("leave")
                .addClasspathResource("diagrams/leave/leave.bpmn")
                .deploy();
        redirectAttributes.addFlashAttribute("message","部署成功");
        return "redirect:/apps/leave/leave";
    }

  /**
   * 任务列表
   */
  @RequestMapping(value = "list/task")
  public ModelAndView taskList(@ModelAttribute("userid") String userid, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/apps/leave/leave");
    Page<Leave> page = new Page<Leave>(PageUtil.PAGE_SIZE);
    int[] pageParams = PageUtil.init(page, request);
    workflowService.findTodoTasks(userid, page, pageParams);
    mav.addObject("page", page);
    return mav;
  }

  /**
   * 读取运行中的流程实例
   * 
   * @return
   */
  @RequestMapping(value = "list/running")
  public ModelAndView runningList(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/apps/leave/running");
    Page<Leave> page = new Page<Leave>(PageUtil.PAGE_SIZE);
    int[] pageParams = PageUtil.init(page, request);
    workflowService.findRunningProcessInstaces(page, pageParams);
    mav.addObject("page", page);
    return mav;
  }

  /**
   * 读取已结束的流程实例
   * 
   * @return
   */
  @RequestMapping(value = "list/finished")
  public ModelAndView finishedList(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/apps/leave/finished");
    Page<Leave> page = new Page<Leave>(PageUtil.PAGE_SIZE);
    int[] pageParams = PageUtil.init(page, request);
    workflowService.findFinishedProcessInstaces(page, pageParams);
    mav.addObject("page", page);
    return mav;
  }

  /**
   * 签收任务
   */
  @RequestMapping(value = "task/claim/{id}")
  public String claim(@PathVariable("id") String taskId, @ModelAttribute("userid") String userid, HttpSession session, RedirectAttributes redirectAttributes) {
    taskService.claim(taskId, userid);
    redirectAttributes.addFlashAttribute("message", "任务已签收");
    return "redirect:/leave/list/task";
  }

  /**
   * 读取详细数据
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "detail/{id}")
  @ResponseBody
  public Leave getExperiment(@PathVariable("id") String id) {
    Leave leave = leaveService.selectLeaveById(id);
    return leave;
  }

  /**
   * 读取详细数据
   * @param id
   * @return
   */
  @RequestMapping(value = "detail-with-vars/{id}/{taskId}")
  @ResponseBody
  public Leave getExperimentWithVars(@PathVariable("id") String id, @PathVariable("taskId") String taskId) {
    Leave leave = leaveService.selectLeaveById(taskId);
    Map<String, Object> variables = taskService.getVariables(taskId);
    leave.setVariables(variables);
    return leave;
  }

  /**
   * 完成任务
   * 
   * @param taskId
   * @return
   */
  @RequestMapping(value = "complete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
  @ResponseBody
  public String complete(@PathVariable("id") String taskId, Variable var) {
    try {
      Map<String, Object> variables = var.getVariableMap();
      taskService.complete(taskId, variables);
      return "success";
    } catch (Exception e) {
      logger.error("error on complete task {}, variables={}", new Object[] { taskId, var.getVariableMap(), e });
      return "error";
    }
  }

  /**
   * 完成任务
   * @param taskId
   * @return
   */
  @RequestMapping(value = "complete1/{id}/{var}", method = { RequestMethod.POST, RequestMethod.GET })
  public String complete1(@PathVariable("id") String taskId, @PathVariable("var") boolean var, RedirectAttributes redirectAttributes) {
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("pass",var);
      taskService.complete(taskId, map);
      redirectAttributes.addFlashAttribute("message","完成步骤");
      return "redirect:/leave/list/task";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error","操作失败");
      return "redirect:/leave/list/task";
    }
  }

}
