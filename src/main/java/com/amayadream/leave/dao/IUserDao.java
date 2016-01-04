package com.amayadream.leave.dao;

import com.amayadream.leave.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:35
 * TODO   :
 */
@Service("userDao")
public interface IUserDao {
    List<User> selectAll();

    User selectUserByUserid(String userid);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(String userid);
}
