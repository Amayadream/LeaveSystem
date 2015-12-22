package com.amayadream.leave.service;

import com.amayadream.leave.pojo.Group;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.service
 * Author :  Amayadream
 * Date   :  2015.12.21 22:57
 * TODO   :
 */
public interface IGroupService {
    List<Group> selectAll();
    List<Group> selectGroupByUserid(String userid);
    boolean insert(Group group);
    boolean update(Group group);
    boolean delete(String groupid);
}
