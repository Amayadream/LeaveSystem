package com.amayadream.leave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.controller
 * Author :  Amayadream
 * Date   :  2015.12.22 22:05
 * TODO   :  路由控制器
 */
@Controller
@RequestMapping(value = "")
public class RouteController {

    @RequestMapping(value = "/")
    public String getIndex() {
        return "apps/leave/login";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "apps/leave/login";
    }

    @RequestMapping(value = "leave")
    public String leave() {
        return "apps/leave/leave";
    }

    @RequestMapping(value = "running")
    public String running() {
        return "apps/leave/running";
    }

    @RequestMapping(value = "finished")
    public String finished() {
        return "apps/leave/finished";
    }

    @RequestMapping(value = "start-process")
    public String start_process() {
        return "apps/activiti/start-process";
    }

    @RequestMapping(value = "running-process")
    public String running_process() {
        return "apps/activiti/running-process";
    }

    @RequestMapping(value = "deploy-manage")
    public String deploy_manage() {
        return "apps/activiti/deploy-manage";
    }

    @RequestMapping(value = "model-manage")
    public String model_manage() {
        return "apps/activiti/model-manage";
    }
}
