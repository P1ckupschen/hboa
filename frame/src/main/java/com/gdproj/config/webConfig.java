package com.gdproj.config;

import com.gdproj.interceptor.LoginInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class webConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  //设置所有的请求可以进行跨域
//                .allowedOrigins("http://localhost:9528","http://localhost:8084")  //允许跨域的ip
//                .allowedMethods("*")  //请求的方法 可以不设置 有默认的
//                .allowedHeaders("*"); //请求头 可以不设置 有默认的
//    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/adminLogin/userlogin")             //添加不拦截的请求路径
                .excludePathPatterns("/api/loginBackend")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/swagger-resources/configuration/ui")
                .addPathPatterns("/**");                  //添加需要拦截的路径
    }
    @Bean
    public LoginInterceptors loginInterceptor(){
        return new LoginInterceptors();
    }
}
