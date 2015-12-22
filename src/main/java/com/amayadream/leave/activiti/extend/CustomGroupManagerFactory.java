package com.amayadream.leave.activiti.extend;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NAME   :  Activiti-demo/com.amayadream.demo.extend
 * Author :  Amayadream
 * Date   :  2015.12.15 14:51
 * TODO   :  自定义的Activiti用户会话工厂
 */
public class CustomGroupManagerFactory implements SessionFactory {
    private GroupEntityManager groupEntityManager;

    @Autowired
    public void setGroupEntityManager(GroupEntityManager groupEntityManager) {
        this.groupEntityManager = groupEntityManager;
    }

    public Class<?> getSessionType() {
        // 返回引擎的实体管理器接口
        return GroupIdentityManager.class;
    }

    public Session openSession() {
        // 返回自定义的GroupEntityManager实例
        return groupEntityManager;
    }
}