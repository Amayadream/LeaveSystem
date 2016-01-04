package com.amayadream.leave.controller;

import com.amayadream.leave.pojo.Log;
import com.amayadream.leave.service.ILogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.controller
 * Author :  Amayadream
 * Date   :  2015.12.29 22:55
 * TODO   :
 */
@Controller
@RequestMapping(value = "log")
@SessionAttributes("userid")
public class LogController {
    @Resource
    private ILogService logService;
    @Resource
    private Log log;

    /**
     * 获取所有日志信息
     *
     * @return
     */
    @RequestMapping(value = "all")
    public ModelAndView all() {
        ModelAndView view = new ModelAndView("/apps/leave/log");
        List<Log> list = logService.selectAll();
        view.addObject("result", list);
        return view;
    }

    /**
     * 获取某个用户的所有日志信息
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "{userid}")
    public ModelAndView id(@ModelAttribute("userid") String id, @PathVariable("userid") String userid) {
        ModelAndView view = new ModelAndView("/apps/leave/log");
        List<Log> list;
        if (id.equals(userid)) {
            list = logService.selectLogByUserid(userid);
        } else {
            list = logService.selectLogByUserid(id);
        }
        view.addObject("result", list);
        return view;
    }
}
