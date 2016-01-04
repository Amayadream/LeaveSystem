package com.amayadream.leave.pojo;

import org.springframework.stereotype.Repository;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.pojo
 * Author :  Amayadream
 * Date   :  2015.12.29 14:51
 * TODO   :  操作日志实体
 */
@Repository("log")
public class Log {
    private String id;      //日志编号
    private String userid;  //操作人员编号
    private String time;    //操作时间
    private String type;    //操作类型
    private String detail;  //操作详情
    private String ip;      //操作ip

    /**
     * getter&setter
     *
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
