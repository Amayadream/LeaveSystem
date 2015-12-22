package com.amayadream.leave.activiti.extend;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NAME   :  Activiti-demo/com.amayadream.demo.extend
 * Author :  Amayadream
 * Date   :  2015.12.15 14:52
 * TODO   :  自定义的Activiti用户组会话工厂
 */
public class CustomUserManagerFactory implements SessionFactory {

    private UserEntityManager userEntityManager;

    @Autowired
    public void setUserEntityManager(UserEntityManager userEntityManager) {
        this.userEntityManager = userEntityManager;
    }

    public Class<?> getSessionType() {
        // 返回引擎的实体管理器接口
        return UserIdentityManager.class;
    }

    public Session openSession() {
        // 返回自定义的UserManager实例
        return userEntityManager;
    }
}