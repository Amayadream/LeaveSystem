package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.dao.ILeaveDao;
import com.amayadream.leave.pojo.Leave;
import com.amayadream.leave.service.ILeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:58
 * TODO   :
 */
@Service("leaveService")
public class LeaveServiceImpl implements ILeaveService {

    @Resource private ILeaveDao leaveDao;

    public List<Leave> selectAll() {
        return leaveDao.selectAll();
    }

    public Leave selectLeaveById(String id) {
        return leaveDao.selectLeaveById(id);
    }

    public List<Leave> selectLeaveByUserid(String userid) {
        return leaveDao.selectLeaveByUserid(userid);
    }

    public boolean insert(Leave leave) {
        return leaveDao.insert(leave);
    }

    public boolean update(Leave leave) {
        return leaveDao.update(leave);
    }

    public boolean delete(String id) {
        return leaveDao.delete(id);
    }
}
