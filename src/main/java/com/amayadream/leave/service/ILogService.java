package com.amayadream.leave.service;

import com.amayadream.leave.pojo.Log;

import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.service
 * Author :  Amayadream
 * Date   :  2015.12.29 15:01
 * TODO   :
 */

public interface ILogService {
    /**
     * 查询全部操作信息
     * @return
     */
    List<Log> selectAll();

    /**
     * 查询某个人的全部操作信息
     * @param userid
     * @return
     */
    List<Log> selectLogByUserid(String userid);

    /**
     * 添加日志
     * @param log
     * @return
     */
    boolean insert(Log log);

    /**
     * 修改日志...备用
     * @param log
     * @return
     */
    boolean update(Log log);

    /**
     * 删除指定日志
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 删除某人全部日志
     * @param userid
     * @return
     */
    boolean deleteAll(String userid);
}
