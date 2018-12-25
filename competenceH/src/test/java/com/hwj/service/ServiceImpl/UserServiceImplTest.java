package com.hwj.service.ServiceImpl;

import com.hwj.entity.User;
import com.hwj.service.IService.IUserService;
import com.hwj.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    IUserService userService;

    @Resource
    RedisUtil redisUtil;

    @Test
    public void competence(){
        List<Integer> list=new ArrayList<>();
        User user=new User();
        list.add(1001);
        list.add(1002);
        list.add(1003);
        user.setEmail("exception@oriente.com");
        user.setPassword("exception");
        userService.addUser(user,list);
    }

    @Test
    public void redis(){
        redisUtil.set("1","123");
    }

    @Test
    public void get(){
        System.out.println(redisUtil.get("1"));
    }

    @Test
    public void Test2(){
        User user=userService.findUserbyId(1002);
        System.out.println(user.toString());
    }

}