package com.amayadream.leave.pojo;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:27
 * TODO   :
 */
@Repository("leave")
public class Leave {
    private String id;                  //编号
    private String processinstanceid;   //流程id
    private String userid;              //用户id
    private String starttime;           //开始时间
    private String endtime;             //结束时间
    private String leaveType;           //假种
    private String reason;              //请假原因
    private String applytime;           //申请时间
    private String realiystarttime;    //实际开始时间
    private String realiyendtime;      //实际结束时间

    // -- 临时属性 -- //
    private Task task;       //流程任务
    private Map<String, Object> variables;      //
    private ProcessInstance processInstance;    //运行中的流程实例
    private HistoricProcessInstance historicProcessInstance;    //历史的流程实例
    private ProcessDefinition processDefinition;    //流程定义

    /**
     * getter&setter
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(String processinstanceid) {
        this.processinstanceid = processinstanceid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public String getRealiystarttime() {
        return realiystarttime;
    }

    public void setRealiystarttime(String realiystarttime) {
        this.realiystarttime = realiystarttime;
    }

    public String getRealiyendtime() {
        return realiyendtime;
    }

    public void setRealiyendtime(String realiyendtime) {
        this.realiyendtime = realiyendtime;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }
}
