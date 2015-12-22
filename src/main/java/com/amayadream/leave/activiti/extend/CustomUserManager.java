package com.amayadream.leave.activiti.extend;

import com.amayadream.leave.service.IUserService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * NAME   :  Activiti-demo/com.amayadream.demo.extend
 * Author :  Amayadream
 * Date   :  2015.12.15 14:52
 * TODO   :  自定义的Activiti用户组管理器
 */
public class CustomUserManager extends UserEntityManager {
    @Resource
    private IUserService userService;
    /**
     * 创建一个User实体
     * @param userId 用户id,实际为用户名
     * @return 用户实体
     */
    public User createNewUser(String userId) {
        // TODO Auto-generated method stub
        System.out.println("createNewUser");
        return super.createNewUser(userId);
    }

    /**
     * 创建新用户
     * @param user 用户对象
     */
    public void insertUser(User user) {
        // TODO Auto-generated method stub
        System.out.println("insertUser");
        super.insertUser(user);
    }

    /**
     * 更新用户
     * @param updatedUser 用户对象
     */
    public void updateUser(User updatedUser) {
        // TODO Auto-generated method stub
        System.out.println("updateUser");
        super.updateUser(updatedUser);
    }

    /**
     * 根据用户名查询用户信息,用于登陆验证,需要重写(登陆验证时为用上,暂时没看出来用途)
     * @param userId 用户id,实际上是用户名
     * @return
     */
    public User findUserById(String userId) {
        // TODO Auto-generated method stub
        System.out.println("findUserById");
        com.amayadream.leave.pojo.User user = userService.selectUserByUserid(userId);
        User user1 = new UserEntity();
        user1.setId(user.getUserid());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        return user1;
    }

    /**
     * 根据用户名删除用户
     * @param userId 用户id,实际为用户名
     */
    public void deleteUser(String userId) {
        // TODO Auto-generated method stub
        System.out.println("deleteUser");
        super.deleteUser(userId);
    }

    /**
     * 获取用户列表,QueryCriteria为查询条件,需要重写
     * @param query 查询条件
     * @param page  page对象,包括起始值与临界值
     * @return 用户list
     */
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        // TODO Auto-generated method stub
        System.out.println("findUserByQueryCriteria");
        com.amayadream.leave.pojo.User user = userService.selectUserByUserid(query.getId());
        User user1 = new UserEntity();
        user1.setId(user.getUserid());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        List<User> list = new ArrayList<User>();
        list.add(user1);
        return list;
//        return super.findUserByQueryCriteria(query, page);
    }

    /**
     * 与findUserByQueryCriteria组合使用,查询符合query条件的用户数量
     * @param query 查询条件
     * @return 符合条件的用户数量
     */
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        // TODO Auto-generated method stub
        System.out.println("findUserCountByQueryCriteria");
        return super.findUserCountByQueryCriteria(query);
    }

    /**
     * 根据用户名查询用户所在的组,需要重写
     * @param userId 用户名
     * @return 分组list
     */
    public List<Group> findGroupsByUser(String userId) {
        System.out.println("user=>findGroupsByUser");
        return super.findGroupsByUser(userId);
    }

    /**
     * 创建新的查询条件,暂时不需要重写
     * @return 查询条件
     */
    public UserQuery createNewUserQuery() {
        // TODO Auto-generated method stub
        System.out.println("createNewUserQuery");
        return super.createNewUserQuery();
    }

    /**
     * 查询用户具体信息
     * @param userId 用户id,实际为用户名
     * @param key 关键字
     * @return 用户信息实体
     */
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        // TODO Auto-generated method stub
        System.out.println("findUserInfoByUserIdAndKey");
        return super.findUserInfoByUserIdAndKey(userId, key);
    }

    /**
     * 查询用户具体信息
     * @param userId 用户id,实际为用户名
     * @param type 类型
     * @return 用户信息实体
     */
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        // TODO Auto-generated method stub
        System.out.println("findUserInfoKeysByUserIdAndType");
        return super.findUserInfoKeysByUserIdAndType(userId, type);
    }

    /**
     * 登陆检查密码
     * @param userId   用户id,实际为用户名
     * @param password 密码
     * @return 返回布尔值 true|false
     */
    public Boolean checkPassword(String userId, String password) {
        // TODO Auto-generated method stub
        com.amayadream.leave.pojo.User user = userService.selectUserByUserid(userId);
        if(user == null){
            return false;
        }else{
            if(password.equals(user.getPassword())){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 根据流程定义id查询候选人list,暂时没用到
     * @param proceDefId 流程定义id
     * @return
     */
    public List<User> findPotentialStarterUsers(String proceDefId) {
        // TODO Auto-generated method stub
        System.out.println("findPotentialStarterUsers");
        return super.findPotentialStarterUsers(proceDefId);
    }

    /**
     * 查询用户list,原始查询,暂时没用到
     * @param parameterMap
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        // TODO Auto-generated method stub
        System.out.println("findUsersByNativeQuery");
        return super.findUsersByNativeQuery(parameterMap, firstResult, maxResults);
    }

    /**
     * 和findUsersByNativeQuery结合,查询符合条件的用户总数
     * @param parameterMap
     * @return
     */
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        // TODO Auto-generated method stub
        System.out.println("findUserCountByNativeQuery");
        return super.findUserCountByNativeQuery(parameterMap);
    }


}