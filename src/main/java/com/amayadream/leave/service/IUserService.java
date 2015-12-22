package com.amayadream.leave.service;

import com.amayadream.leave.pojo.User;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.service
 * Author :  Amayadream
 * Date   :  2015.12.21 22:57
 * TODO   :
 */
public interface IUserService {
    List<User> selectAll();
    User selectUserByUserid(String userid);
    boolean insert(User user);
    boolean update(User user);
    boolean delete(String userid);
}
