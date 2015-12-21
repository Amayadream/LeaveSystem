package com.amayadream.leave.activiti.service.leave;

import com.amayadream.demo.pojo.Experiment;
import com.amayadream.demo.service.IExperimentService;
import com.amayadream.demo.util.DateUtil;
import com.amayadream.demo.util.Page;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 请假流程Service
 * 
 * @author HenryYan
 */
@Component
@Transactional
public class ExperimentWorkflowService {
  private static Logger logger = LoggerFactory.getLogger(ExperimentWorkflowService.class);
  @Autowired private RuntimeService runtimeService;
  @Autowired protected TaskService taskService;
  @Autowired protected HistoryService historyService;
  @Autowired protected RepositoryService repositoryService;
  @Autowired private IdentityService identityService;
  @Resource private IExperimentService experimentService;
  @Resource private Experiment experiment;

  /**
   * 启动流程
   * @param experiment  实验实体
   * @param variables   各种参数
   * @return
     */
  public ProcessInstance startWorkflow(Experiment experiment, Map<String, Object> variables) {
    DateUtil dateUtil = new DateUtil();
    experiment.setStarttime(dateUtil.getDateTime24());
    experimentService.insert(experiment);
    String businessKey = experiment.getId();
    // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
    identityService.setAuthenticatedUserId(experiment.getUserid());
//    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("experiment", businessKey, variables);
    ProcessInstance processInstance = runtimeService.startProcessInstanceById("experiment:3:5026", businessKey, variables);
    String processInstanceId = processInstance.getId();
    experiment.setProcessinstanceid(processInstanceId);
    experimentService.update(experiment);
    logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[] { "experiment", businessKey, processInstanceId, variables });
    return processInstance;
  }

  /**
   * 根据key启动流程
   * @param key
   * @param experiment
   * @param variables
     * @return
     */
  public ProcessInstance start(String id, Experiment experiment, Map<String, Object> variables){
    DateUtil dateUtil = new DateUtil();
    experiment.setStarttime(dateUtil.getDateTime24());
    experimentService.insert(experiment);
    String businessKey = experiment.getId();
    // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
    identityService.setAuthenticatedUserId(experiment.getUserid());
    ProcessInstance processInstance = runtimeService.startProcessInstanceById(id, businessKey, variables);
    String processInstanceId = processInstance.getId();
    experiment.setProcessinstanceid(processInstanceId);
    experimentService.update(experiment);
    return processInstance;
  }

  /**
   * 查询待办任务
   * 
   * @param userid
   *          用户ID
   * @return
   */
  @Transactional(readOnly = true)
  public List<Experiment> findTodoTasks(String userid, Page<Experiment> page, int[] pageParams) {
    List<Experiment> results = new ArrayList<Experiment>();
    List<Task> tasks = new ArrayList<Task>();

    // 根据当前人的ID查询
    TaskQuery todoQuery = taskService.createTaskQuery().processDefinitionKey("experiment").taskAssignee(userid).active().orderByTaskId().desc()
            .orderByTaskCreateTime().desc();

    List<Task> todoList = todoQuery.listPage(pageParams[0], pageParams[1]);

    // 根据当前人未签收的任务
    TaskQuery claimQuery = taskService.createTaskQuery().processDefinitionKey("experiment").taskCandidateUser(userid).active().orderByTaskId().desc()
            .orderByTaskCreateTime().desc();
    List<Task> unsignedTasks = claimQuery.listPage(pageParams[0], pageParams[1]);

    // 合并
    tasks.addAll(todoList);
    tasks.addAll(unsignedTasks);

    // 根据流程的业务ID查询实体并关联
    for (Task task : tasks) {
      String processInstanceId = task.getProcessInstanceId();
      ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
      String businessKey = processInstance.getBusinessKey();
      Experiment experiment = experimentService.selectExperimentById(businessKey);
      experiment.setTask(task);
      experiment.setProcessInstance(processInstance);
      experiment.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
      results.add(experiment);
    }
    
    page.setTotalCount(todoQuery.count() + claimQuery.count());
    page.setResult(results);
    return results;
  }

  /**
   * 读取运行中的流程
   * 
   * @return
   */
  @Transactional(readOnly = true)
  public List<Experiment> findRunningProcessInstaces(Page<Experiment> page, int[] pageParams) {
    List<Experiment> results = new ArrayList<Experiment>();
    ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("experiment").active().orderByProcessInstanceId().desc();
    List<ProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

    // 关联业务实体
    for (ProcessInstance processInstance : list) {
      String businessKey = processInstance.getBusinessKey();
      Experiment experiment = experimentService.selectExperimentById(businessKey);
      experiment.setProcessInstance(processInstance);
      experiment.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
      results.add(experiment);

      // 设置当前任务信息
      List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().listPage(0, 1);
      experiment.setTask(tasks.get(0));
    }
    page.setTotalCount(query.count());
    page.setResult(results);
    return results;
  }

  /**
   * 读取已结束中的流程
   * 
   * @return
   */
  @Transactional(readOnly = true)
  public List<Experiment> findFinishedProcessInstaces(Page<Experiment> page, int[] pageParams) {
    List<Experiment> results = new ArrayList<Experiment>();
    HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("experiment").finished().orderByProcessInstanceEndTime().desc();
    List<HistoricProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

    // 关联业务实体
    for (HistoricProcessInstance historicProcessInstance : list) {
      String businessKey = historicProcessInstance.getBusinessKey();
      Experiment experiment = experimentService.selectExperimentById(businessKey);
      experiment.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
      experiment.setHistoricProcessInstance(historicProcessInstance);
      results.add(experiment);
    }
    page.setTotalCount(query.count());
    page.setResult(results);
    return results;
  }

  /**
   * 查询流程定义对象
   * 
   * @param processDefinitionId
   *          流程定义ID
   * @return
   */
  protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    return processDefinition;
  }

}
