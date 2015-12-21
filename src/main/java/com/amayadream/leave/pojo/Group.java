package com.amayadream.leave.pojo;

import org.springframework.stereotype.Repository;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:27
 * TODO   :
 */
@Repository("group")
public class Group {
    private String groupid;      //分组编号
    private String name;    //分组名
    private String type;    //类型

    /**
     * getter&setter
     * @return
     */
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
