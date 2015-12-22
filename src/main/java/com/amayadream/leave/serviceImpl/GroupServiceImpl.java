package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.dao.IGroupDao;
import com.amayadream.leave.pojo.Group;
import com.amayadream.leave.service.IGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:57
 * TODO   :
 */
@Service("groupService")
public class GroupServiceImpl implements IGroupService {

    @Resource private IGroupDao groupDao;

    public List<Group> selectAll() {
        return groupDao.selectAll();
    }

    public List<Group> selectGroupByUserid(String userid) {
        return groupDao.selectGroupByUserid(userid);
    }

    public boolean insert(Group group) {
        return groupDao.insert(group);
    }

    public boolean update(Group group) {
        return groupDao.update(group);
    }

    public boolean delete(String groupid) {
        return groupDao.delete(groupid);
    }
}
