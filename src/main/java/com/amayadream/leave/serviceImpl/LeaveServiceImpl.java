package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.pojo.Leave;
import com.amayadream.leave.service.ILeaveService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:58
 * TODO   :
 */
@Service("leaveService")
public class LeaveServiceImpl implements ILeaveService {
    public List<Leave> selectAll() {
        return null;
    }

    public Leave selectLeaveById(String id) {
        return null;
    }

    public List<Leave> selectLeaveByUserid(String userid) {
        return null;
    }

    public boolean insert(Leave leave) {
        return false;
    }

    public boolean update(Leave leave) {
        return false;
    }

    public boolean delete(String id) {
        return false;
    }
}
