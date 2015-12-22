package com.amayadream.leave.activiti.extend;

/**
 * NAME   :  Activiti-demo/com.amayadream.demo.extend
 * Author :  Amayadream
 * Date   :  2015.12.15 14:48
 * TODO   :
 */

import com.amayadream.leave.service.IGroupService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义的Activiti用户组管理器
 *
 */
public class CustomGroupManager extends GroupEntityManager {

    @Resource
    private IGroupService groupService;

    public Group createNewGroup(String groupId) {
        // TODO Auto-generated method stub
        System.out.println("createNewGroup");
        return super.createNewGroup(groupId);
    }

    public void insertGroup(Group group) {
        // TODO Auto-generated method stub
        System.out.println("insertGroup");
        super.insertGroup(group);
    }

    public void updateGroup(Group updatedGroup) {
        // TODO Auto-generated method stub
        System.out.println("updateGroup");
        super.updateGroup(updatedGroup);
    }

    public void deleteGroup(String groupId) {
        // TODO Auto-generated method stub
        System.out.println("deleteGroup");
        super.deleteGroup(groupId);
    }

    //创建查询
    public GroupQuery createNewGroupQuery() {
        // TODO Auto-generated method stub
        System.out.println("createNewGroupQuery");
        return super.createNewGroupQuery();
    }

    //获取所有的分组列表,QueryCriteria是查询附加的条件
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        // TODO Auto-generated method stub
        return super.findGroupByQueryCriteria(query, page);
    }

    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        // TODO Auto-generated method stub
        System.out.println("findGroupCountByQueryCriteria");
        return super.findGroupCountByQueryCriteria(query);
    }

    //需要重写,分次使用
    public List<Group> findGroupsByUser(String userId) {
        // TODO Auto-generated method stub
        List<com.amayadream.leave.pojo.Group> list = groupService.selectGroupByUserid(userId);
        List<Group> list1 = new ArrayList<Group>();
        for(com.amayadream.leave.pojo.Group group : list){
            Group group1 = new GroupEntity();
            group1.setId(group.getGroupid());
            group1.setName(group.getName());
            group1.setType(group.getType());
            list1.add(group1);
        }
        return list1;
//        return super.findGroupsByUser(userId);
    }

    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        // TODO Auto-generated method stub
        System.out.println("findGroupsByNativeQuery");
        return super.findGroupsByNativeQuery(parameterMap, firstResult, maxResults);
    }

    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        // TODO Auto-generated method stub
        System.out.println("findGroupCountByNativeQuery");
        return super.findGroupCountByNativeQuery(parameterMap);
    }

    public boolean isNewGroup(Group group){
        System.out.println("isNewGroup");
        return super.isNewGroup(group);
    }
}