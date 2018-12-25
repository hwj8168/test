//package com.hwj.interceptor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * @Auther: hwj
// * @Date: 2018/12/13 16:31
// * @Description:
// */
//@Configuration
//public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
//    //拦截器在自动初始化bean之前，而拦截器又使用了其他的bean所以要注册拦截器bean
//    @Bean
//    public MyInterceptor myInterceptor() {
//        return new MyInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //除了登陆界面 其他请求拦截
//        registry.addInterceptor(myInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login","/user/getAllResources","classpath:/static/");
//    }
//
//    /**
//     * @Description: 放行静态资源，用不到了
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        super.addResourceHandlers(registry);
//    }
//
//
//
//}