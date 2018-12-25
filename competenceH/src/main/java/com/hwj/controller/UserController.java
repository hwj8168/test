package com.hwj.controller;


import com.hwj.dto.Code;
import com.hwj.dto.ResourceDto;
import com.hwj.dto.ResponseData;
import com.hwj.dto.UserDto;
import com.hwj.entity.User;
import com.hwj.service.IService.IUserService;
import com.hwj.utils.MD5Util;
import com.hwj.utils.RedisUtil;
import com.hwj.utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    IUserService userService;

    @Resource
    RedisUtil redisUtil;

    /**
     * @Description: 用户登陆
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestBody User u, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        UserDto userDto = new UserDto();
        //根据email得到user信息
        User user = userService.login(u.getEmail());
        if (user == null) {
            //用户名不存在
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("用户不存在!");
            return responseData;
        }
        //得到md5加密后的密码
        String password = MD5Util.md5Password(u.getPassword() + user.getSalt());
        if (!user.getPassword().equals(password)) {
            //用户存在但密码不匹配
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("密码错误!");
            return responseData;
        } else {
            //用户登陆成功后生成token签名,将token作为key存入redis,useId为value
            String token = TokenUtil.getToken();
            redisUtil.set(token, user.getId());
            //token放到response header里
            response.setHeader("token", token);
            //设置响应能够访问的http header
            response.setHeader("Access-Control-Expose-Headers", "token");
            //得到用户拥有的资源权限
            User user1 = userService.competence(user.getId());
            //封装前台需要的数据
            userDto.setEmail(user.getEmail());
            userDto.setId(user.getId());
            userDto.setType(user.getType());
            responseData.setCode(Code.SUCCESS.getCode());
            responseData.setMessage(Code.SUCCESS.getMessage());
            //如果登陆用户没有任何资源权限
            if (user1 == null) {
                responseData.putDataValue("user", userDto);
                return responseData;
            }
            //提取用户的所需资源权限的字段
            List<ResourceDto> resourceDtoList = new ArrayList<>();
            for (com.hwj.entity.Resource resource : user1.getResourceList()) {
                ResourceDto resourceDto = new ResourceDto();
                resourceDto.setName(resource.getName());
                resourceDto.setUrl(resource.getUrl());
                resourceDto.setNation(resource.getNation());
                resourceDtoList.add(resourceDto);
            }
            userDto.setResourceList(resourceDtoList);
            responseData.putDataValue("user", userDto);
            return responseData;
        }

    }

    //用户资源判断
/*
    @RequestMapping("/ssoUrl")
    public String sso(@RequestParam("url") String url, HttpServletRequest request,HttpServletResponse response) {
        String token = request.getHeader("token");
        int userId = (int) redisUtil.get(token);
        User user = userService.competence(userId);
        //判断用户请求的资源是否有权限
        List<com.hwj.entity.Resource> resourceList = user.getResourceList();
        for (com.hwj.entity.Resource resource : resourceList) {
            if (resource.getUrl().equals(url)) {
                System.out.println("访问界面：" + url);
                return "url";
            }
        }
        response.setStatus(401);
        System.out.println("该用户没有权限访问");
        return "login";
    }*/


    /**
     * @Description: 用户修改密码
     */
    @RequestMapping("/updatePwd")
    @ResponseBody
    public ResponseData updatePwd(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        String token = request.getHeader("token");
        if (token == null || redisUtil.get(token) == null) {
            responseData.setCode(100);
            responseData.setMessage("请登陆");
            return responseData;
        }
        String oldPwd = (String) map.get("oldPwd");
        int userId = (int) redisUtil.get(request.getHeader("token"));
        User user = userService.findUserbyId(userId);
        //md5加密后的旧密码
        String md5OldPwd = MD5Util.md5Password(oldPwd + user.getSalt());
        //如果旧密码输入成功
        if (md5OldPwd.equals(user.getPassword())) {
            String newPwd = (String) map.get("newPwd");
            //md5加密后的新密码
            String md5NewPWd = MD5Util.md5Password(newPwd + user.getSalt());
            boolean result = userService.updatePwd(md5NewPWd, userId);
            //修改密码成功
            if (result) {
                responseData.setCode(Code.SUCCESS.getCode());
                responseData.setMessage(Code.SUCCESS.getMessage());
                return responseData;
            } else {
                responseData.setCode(Code.Fail.getCode());
                responseData.setMessage("修改失败");
                return responseData;
            }
        } else {
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("旧密码错误");
            return responseData;
        }
    }

    /**
     * @Description: 管理员重置密码
     */
    @RequestMapping("/reSetPwd")
    @ResponseBody
    public ResponseData resetPwd(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        String token = request.getHeader("token");
        if (token == null || redisUtil.get(token) == null) {
            responseData.setCode(100);
            responseData.setMessage("请登陆");
            return responseData;
        }
        //判断是否是管理员
        if (!valid(request)) {
            response.setStatus(401);
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("无权访问");
            return responseData;
        }
        String email = (String) map.get("email");
        User user = userService.selectByEmail(email);
        if (user == null) {
            System.out.println("333");
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("用户不存在");
            return responseData;
        }
        String newPwd = MD5Util.md5Password("orientePwd" + user.getSalt());
        boolean res = userService.updatePwd(newPwd, user.getId());
        if (res) {
            responseData.setCode(Code.SUCCESS.getCode());
            responseData.setMessage(Code.SUCCESS.getMessage());
            return responseData;
        } else {
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage(Code.Fail.getMessage());
            return responseData;
        }
    }

    /**
     * @Description: 获取所有的资源
     */
    @RequestMapping("/getAllResources")
    @ResponseBody
    public ResponseData getAllRes(HttpServletResponse response, HttpServletRequest request) {
        ResponseData responseData = new ResponseData();
        String token = request.getHeader("token");
        if (token == null || redisUtil.get(token) == null) {
            responseData.setCode(100);
            responseData.setMessage("请登陆");
            return responseData;
        }
        responseData.setCode(Code.SUCCESS.getCode());
        responseData.setMessage(Code.SUCCESS.getMessage());
        responseData.putDataValue("resources", userService.getALlResource());
        return responseData;
    }

    /**
     * @Description: 增加用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public ResponseData addUser(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //用户是否登陆
        ResponseData responseData = new ResponseData();
        String token = request.getHeader("token");
        if (token == null || redisUtil.get(token) == null) {
            responseData.setCode(100);
            responseData.setMessage("请登陆");
            return responseData;
        }
        //判断是否是管理员
        if (!valid(request)) {
            response.setStatus(401);
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("无权访问");
            return responseData;
        }
        User user = new User();
        user.setEmail((String) map.get("email"));
        user.setType((String) map.get("type"));
        //密码进行md5加密 加盐值
        String salt = MD5Util.salt();
        user.setSalt(salt);
        //密码默认orientePwd
        String password = MD5Util.md5Password("orientePwd" + salt);
        user.setPassword(password);
        List<Integer> list = (List<Integer>) map.get("competenceList");
        boolean result = userService.addUser(user, list);
        //增加成功
        if (result) {
            responseData.setCode(Code.SUCCESS.getCode());
            responseData.setMessage(Code.SUCCESS.getMessage());
            return responseData;
        } else {
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("用户已存在");
            return responseData;
        }
    }

    /**
     * @Description: 修改用户权限
     */
    @RequestMapping("/updateCompetence")
    @ResponseBody
    public ResponseData updateCompetence(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        //是否登陆
        String token = request.getHeader("token");
        if (token == null || redisUtil.get(token) == null) {
            responseData.setCode(100);
            responseData.setMessage("请登陆");
            return responseData;
        }
        //判断是否是管理员
        if (!valid(request)) {
            response.setStatus(401);
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("无权访问");
            return responseData;
        }
        List<Integer> list = (List<Integer>) map.get("competenceList");
        String email = (String) map.get("email");
        User user = userService.selectByEmail(email);
        if (user == null) {
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage("用户不存在");
            return responseData;
        }
        boolean result = userService.updateCompetence(user.getId(), list);
        if (result) {
            responseData.setCode(Code.SUCCESS.getCode());
            responseData.setMessage(Code.SUCCESS.getMessage());
            return responseData;
        } else {
            responseData.setCode(Code.Fail.getCode());
            responseData.setMessage(Code.Fail.getMessage());
            return responseData;
        }
    }


    /**
     * @Description: 判断用户是否有权限
     */
    public boolean valid(HttpServletRequest request) {
        String token = request.getHeader("token");
        int userId = (int) redisUtil.get(token);
        //判断是否是管理员
        String type = userService.findUserbyId(userId).getType();
        //用户没有权限访问
        if (type.equals("管理员")) {
            return true;
        }
        return false;
    }


    @RequestMapping("/hwj")
    public String test() {
        return "test";
    }
}
