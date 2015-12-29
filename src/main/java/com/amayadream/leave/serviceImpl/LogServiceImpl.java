package com.amayadream.leave.serviceImpl;

import com.amayadream.leave.dao.ILogDao;
import com.amayadream.leave.pojo.Log;
import com.amayadream.leave.service.ILogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.serviceImpl
 * Author :  Amayadream
 * Date   :  2015.12.29 15:02
 * TODO   :
 */
@Service("logService")
public class LogServiceImpl implements ILogService {

    @Resource private ILogDao logDao;

    public List<Log> selectAll() {
        return logDao.selectAll();
    }

    public List<Log> selectLogByUserid(String userid) {
        return logDao.selectLogByUserid(userid);
    }

    public boolean insert(Log log) {
        return logDao.insert(log);
    }

    public boolean update(Log log) {
        return logDao.update(log);
    }

    public boolean delete(String id) {
        return logDao.delete(id);
    }

    public boolean deleteAll(String userid) {
        return logDao.deleteAll(userid);
    }
}
