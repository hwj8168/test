package com.hwj.dao;

import com.hwj.entity.Resource;
import com.hwj.entity.User;
import com.hwj.entity.UserResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //验证用户名密码
    public User login(@Param("email") String email);
    //通过userId查询用户信息
    public User findUserById(int userId);

    //查询登陆用户的权限
    public User competence(int id);

    //增加用户
    public int addUser(User user);

    //增加用户的权限
    public int addCompetence(@Param("list") List<UserResource> list);

    //通过email查询用户
    public User selectUseByEmail(String email);

    //修改用户密码
    public int updateUserPwd(@Param("pwd") String pwd,@Param("id") int userId);

    //修改用户权限，user_id  resource_id  没有办法按主键修改
    // 解决方案1 先将该用户的所属权限删除再重新增加修改后的权限
    public int deleteUseCpmpetence(int userId);
    //得到所有资源
    public List<Resource> getAllRes();
}

