package com.hwj.service.IService;

import com.hwj.entity.Resource;
import com.hwj.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * @Description: 登陆
     */
    public User login(String email);

    /**
     * @Description: 查询用户权限
     */
    public User competence(int userId);

    /**
     * @Description: 增加用户
     */
    public boolean addUser(User user, List<Integer> list);

    /**
     * @Description: 修改密码
     */
    public boolean updatePwd(String pwd,int id);

    /**
     * @Description:修改用户权限
     * 因为user_resource表中需要更新好几条数据，不固定条数
     * 所以目前解决方法是先删除某个用户的所属权限，然后再将新修改的权限重新insert到user_resource表中
     */
    public boolean updateCompetence(int userId, List<Integer> list);
    
    /** 
    * @Description:  通过用户名查询用户信息
    */ 
    public User selectByEmail(String email);

    public User findUserbyId(int id);

    /**
    * @Description: 获取所有资源
    */
    public List<Resource> getALlResource();
}
