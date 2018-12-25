package com.hwj.dao;

import com.hwj.entity.User;
import com.hwj.entity.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void login() {
        User user = userMapper.login("hwj@oriente.com");
        System.out.println(user.toString());
    }

    @Test
    public void competence() {
        User user = userMapper.competence(1001);
        System.out.println(user.toString());
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("add2@oriente.com");
        user.setPassword("add2User");
        userMapper.addUser(user);
        System.out.println("test:" + user.getId());
    }


    @Test
    public void addCompentence() {
        List<UserResource> list = new ArrayList<>();
        UserResource userResource = new UserResource();
        userResource.setUserId(1003);
        userResource.setResourceId(1001);
        list.add(userResource);

        UserResource userResource1 = new UserResource();
        userResource1.setUserId(1003);
        userResource1.setResourceId(1002);
        list.add(userResource1);

        UserResource userResource2 = new UserResource();
        userResource2.setUserId(1003);
        userResource2.setResourceId(1003);
        list.add(userResource2);
        userMapper.addCompetence(list);
    }

    @Test
    public void selectByName() {
        User user = userMapper.selectUseByEmail("tes@oriente.com");
        if (user != null) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }

    @Test
    public void updatePwd() {
        userMapper.updateUserPwd("test1",1002);
    }

    @Test
    public void deleteComp(){
        userMapper.deleteUseCpmpetence(1005);
    }
    @Test
    public void getAll(){
        List<com.hwj.entity.Resource> list=userMapper.getAllRes();
        for(com.hwj.entity.Resource resource:list){
            System.out.println(resource.toString());
        }
        System.out.println(list.size());

    }


}