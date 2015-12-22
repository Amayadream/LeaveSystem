package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.pojo.User;
import com.amayadream.leave.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:58
 * TODO   :
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    public List<User> selectAll() {
        return null;
    }

    public User selectUserByUserid(String userid) {
        return null;
    }

    public boolean insert(User user) {
        return false;
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(String userid) {
        return false;
    }
}
