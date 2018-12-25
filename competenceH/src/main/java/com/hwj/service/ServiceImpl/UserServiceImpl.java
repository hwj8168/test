package com.hwj.service.ServiceImpl;

import com.hwj.dao.UserMapper;
import com.hwj.entity.User;
import com.hwj.entity.UserResource;
import com.hwj.service.IService.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    UserMapper userMapper;

    /**
     * @Description: 用户登陆
     */
    @Override
    public User login(String email) {
        User user = userMapper.login(email);
        return user;
    }


    /**
     * @Description: 得到登陆用户的权限
     */
    @Override
    public User competence(int userId) {
        User user = userMapper.competence(userId);
        return user;
    }

    /**
     * @Description: 增加用户
     * 需要向两个表中添加数据 user表和user_resource表 必须先向user表中添加数据
     * 因为user_resource表需要用到user表中自动生成的主键
     */
    @Override
    @Transactional
    public boolean addUser(User user, List<Integer> list) {
        //如果该用户存在 不能添加
        if (userMapper.selectUseByEmail(user.getEmail()) != null) {
            return false;
        }
        int resadd = userMapper.addUser(user);
        if (resadd == 0) {
            return false;
        }
        //插入到user表后得到自增的主键
        int id = user.getId();
        //批量插入到user_resource表中
        int result = competence(id, list);
        if (result != 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 修改密码
     */
    @Override
    public boolean updatePwd(String pwd,int id) {
        int result = userMapper.updateUserPwd(pwd,id);
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description:修改用户权限 先删除user_resource表中之前的权限
     * 重新insert修改之后的权限
     */
    @Transactional
    @Override
    public boolean updateCompetence(int userId, List<Integer> list) {
        //先删除之前的权限
        userMapper.deleteUseCpmpetence(userId);
        //将userId与权限list集合进行封装
        List<UserResource> userResourceList = new ArrayList<>();
        for (Integer resourceId : list) {
            UserResource userResource = new UserResource();
            userResource.setUserId(userId);
            userResource.setResourceId(resourceId);
            userResourceList.add(userResource);
        }
        //修改之后的权限重新insert到user_resource表中
        int result = userMapper.addCompetence(userResourceList);
        if (result != 0) {
            return true;
        }
        return false;
    }

    @Override
    public User selectByEmail(String email) {

        return userMapper.selectUseByEmail(email);
    }

    @Override
    public User findUserbyId(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public List<com.hwj.entity.Resource> getALlResource() {
        return userMapper.getAllRes();
    }


    /**
     * @Description: 增加用户的权限
     */
    @Transactional
    public int competence(int id, List<Integer> list) {
        List<UserResource> userResourceList = new ArrayList<>();
        for (Integer resourceId : list) {
            UserResource userResource = new UserResource();
            userResource.setUserId(id);
            userResource.setResourceId(resourceId);
            userResourceList.add(userResource);
        }
        int a = userMapper.addCompetence(userResourceList);
        return a;
    }
}
