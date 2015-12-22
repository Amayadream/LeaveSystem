package com.amayadream.leave.service;

import com.amayadream.leave.pojo.Leave;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.service
 * Author :  Amayadream
 * Date   :  2015.12.21 22:57
 * TODO   :
 */
public interface ILeaveService {
    List<Leave> selectAll();
    Leave selectLeaveById(String id);
    List<Leave> selectLeaveByUserid(String userid);
    boolean insert(Leave leave);
    boolean update(Leave leave);
    boolean delete(String id);
}
