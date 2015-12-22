package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.pojo.Group;
import com.amayadream.leave.service.IGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:57
 * TODO   :
 */
@Service("groupService")
public class GroupServiceImpl implements IGroupService {
    public List<Group> selectAll() {
        return null;
    }

    public List<Group> selectGroupByUserid(String userid) {
        return null;
    }

    public boolean insert(Group group) {
        return false;
    }

    public boolean update(Group group) {
        return false;
    }

    public boolean delete(String groupid) {
        return false;
    }
}
