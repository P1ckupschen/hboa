package com.gdproj.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class logAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static Long starttime = 0L;
    @Pointcut("@annotation(com.gdproj.annotation.autoLog)")
    public void pt(){
    }
    @Before("pt()")
    public void beforeAdvice(JoinPoint joinPoint){
        logger.info("=======Start=======");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

//         打印请求 URL
        logger.info("URL            : {}",request.getRequestURL());
        // 打印 Http method
        logger.info("HTTP Method    : {}",request.getMethod() );
        // 打印调用 controller 的全路径以及执行方法 ？？？
        logger.info("Class Method   : {}.{}",request.getContextPath(),joinPoint.getSignature().getName() );
        // 打印请求的 IP ？？？
        logger.info("IP             : {}",getIpAddress(request));
        // 打印请求入参
        logger.info("Request Args   : {}",joinPoint.getArgs());
        // 打印结果 ？？？
//        logger.info("Response       : {}", );
        // 结束后换行
        logger.info("=======End=======" + System.lineSeparator());
        starttime = System.currentTimeMillis();
    }
    @After("pt()")
    public void afterAdvice(JoinPoint joinPoint){
        Long endtime = System.currentTimeMillis();
        Long  runtime = endtime - starttime ;
        System.out.println("运行时间：{}"+runtime);

    }
//    @AfterThrowing("pt()")
//    public void afterThrowing(JoinPoint joinPoint,Throwable e){
//
//    }

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
