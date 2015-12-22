package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.dao.IUserDao;
import com.amayadream.leave.pojo.User;
import com.amayadream.leave.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.21 22:58
 * TODO   :
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource private IUserDao userDao;

    public List<User> selectAll() {
        return userDao.selectAll();
    }

    public User selectUserByUserid(String userid) {
        return userDao.selectUserByUserid(userid);
    }

    public boolean insert(User user) {
        return userDao.insert(user);
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public boolean delete(String userid) {
        return userDao.delete(userid);
    }
}
