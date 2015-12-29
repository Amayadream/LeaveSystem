package com.amayadream.leave.dao;

import com.amayadream.leave.pojo.Log;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.dao
 * Author :  Amayadream
 * Date   :  2015.12.29 14:55
 * TODO   :
 */
@Service("logDao")
public interface ILogDao {
    List<Log> selectAll();
    List<Log> selectLogByUserid(String userid);
    boolean insert(Log log);
    boolean update(Log log);
    boolean delete(String id);
    boolean deleteAll(String userid);
}
