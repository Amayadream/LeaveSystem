package com.amayadream.leave.pojo;

import org.springframework.stereotype.Repository;

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
    private String realitystarttime;    //实际开始时间
    private String realityendtime;      //实际结束时间

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

    public String getRealitystarttime() {
        return realitystarttime;
    }

    public void setRealitystarttime(String realitystarttime) {
        this.realitystarttime = realitystarttime;
    }

    public String getRealityendtime() {
        return realityendtime;
    }

    public void setRealityendtime(String realityendtime) {
        this.realityendtime = realityendtime;
    }
}
