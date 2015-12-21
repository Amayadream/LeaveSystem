package com.amayadream.leave.dao;

import com.amayadream.leave.pojo.User;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.21 22:35
 * TODO   :
 */
public interface IUserDao {

    List<User> selectAll();

}
