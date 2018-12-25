//package com.hwj.interceptor;
//
//
//import com.hwj.utils.RedisUtil;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Auther: hwj
// * @Date: 2018/12/13 16:18
// * @Description:
// */
//@Component
//public class MyInterceptor implements HandlerInterceptor {
//
//    @Resource
//    RedisUtil redisUtil;
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//
//        String token = httpServletRequest.getHeader("token");
//        if (token == null) {
//            System.out.println("无token，请登陆");
//            return false;
//        }
//        //redis中token存在
//        if (redisUtil.get(token) != null) {
//            System.out.println("token验证成功");
//            return true;
//        }
//        System.out.println("token验证失败");
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}