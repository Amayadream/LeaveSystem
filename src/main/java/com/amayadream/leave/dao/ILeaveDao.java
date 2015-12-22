package com.amayadream.leave.dao;

import com.amayadream.leave.pojo.Leave;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:52
 * TODO   :
 */
@Service("leaveDao")
public interface ILeaveDao {
    List<Leave> selectAll();
    Leave selectLeaveById(String id);
    List<Leave> selectLeaveByUserid(String userid);
    boolean insert(Leave leave);
    boolean update(Leave leave);
    boolean delete(String id);
}
