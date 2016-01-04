package com.amayadream.leave.dao;

import com.amayadream.leave.pojo.Group;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:50
 * TODO   :
 */
@Service("groupDao")
public interface IGroupDao {
    List<Group> selectAll();

    List<Group> selectGroupByUserid(String userid);

    boolean insert(Group group);

    boolean update(Group group);

    boolean delete(String groupid);
}
