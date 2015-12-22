package com.amayadream.leave.controller;

import com.amayadream.leave.pojo.User;
import com.amayadream.leave.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.controller
 * Author :  Amayadream
 * Date   :  2015.12.22 22:06
 * TODO   :
 */
@Controller
@RequestMapping(value = "user")
public class LoginController {

    @Resource private IUserService userService;
    @Resource private User user;

    @RequestMapping(value = "login")
    public String login(@RequestParam("username") String userid, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes){
        user = userService.selectUserByUserid(userid);
        if(user != null){
            if(password.equals(user.getPassword())){
                session.setAttribute("user",user);
                return "redirect:/index";
            }else{
                redirectAttributes.addFlashAttribute("error","用户名或密码错误!请重新登陆!");
                return "redirect:/login";
            }
        }else{
            redirectAttributes.addFlashAttribute("error","查无此账号!");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("message","注销成功!");
        return "redirect:/login";
    }

}
