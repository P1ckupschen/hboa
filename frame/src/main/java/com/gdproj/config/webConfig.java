package com.gdproj.config;

import com.gdproj.interceptor.LoginInterceptors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class webConfig implements WebMvcConfigurer {


    @Value("${ImagesFilePath}")
    private String imageFilePath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //设置所有的请求可以进行跨域
//                .allowedOrigins("http://localhost:9529","http://localhost:9528","192.168.1.41")  //允许跨域的ip 5173
                .allowedOrigins("*")  //允许跨域的ip 5173
                .allowedMethods("*")  //请求的方法 可以不设置 有默认的
                .allowedHeaders("*"); //请求头 可以不设置 有默认的
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/adminLogin/userlogin")             //添加不拦截的请求路径
                .excludePathPatterns("/api/loginBackend")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/images/")
                .addPathPatterns("/**");                  //添加需要拦截的路径
    }
    @Bean
    public LoginInterceptors loginInterceptor(){
        return new LoginInterceptors();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //物理路径 可访问
//        registry.addResourceHandler("/images/**").addResourceLocations("file:E:\\sch-work-space\\projects\\hbkj\\hboa\\admin\\src\\main\\resources\\images\\");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + imageFilePath);
    }

}
