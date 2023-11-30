package com.gdproj.aspect;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.gdproj.entity.Log;
import com.gdproj.service.LogService;
import com.gdproj.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


@Aspect
@Component
public class logAspect {

    @Autowired
    LogService logService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static Long starttime = 0L;
    @Pointcut("@annotation(com.gdproj.annotation.autoLog)")
    public void pt(){
    }
    @Before("pt()")
    public void beforeAdvice(JoinPoint joinPoint){
        starttime = System.currentTimeMillis();
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Method method = ms.getMethod();
        logger.info("===============请求===============");
        logger.info("请求方式:" + request.getMethod());
        logger.info("请求地址:" + point.getTarget().getClass().getName());
        logger.info("方法名称:" + method.getAnnotation(ApiOperation.class).value());
        logger.info("请求类方法:" + point.getSignature().getName());
        logger.info("请求类方法参数:" + JSONUtil.toJsonStr(point.getArgs()));
        logger.info("===============请求===============");
        String token = request.getHeader("Authorization");
        if(!"GET".equals(request.getMethod())){
            Log log = new Log();
            log.setLogContent(method.getAnnotation(ApiOperation.class).value());
            log.setUserIp(getIpAddress(request));
            if(!ObjectUtil.isEmpty(token) &&  JwtUtils.checkToken(token)){
                String subToken = token.substring(7);
                String id = (String) JwtUtils.parseJWT(subToken).get("id");
                log.setUserId(Integer.valueOf(id));
            }
            //    TODO  新增log日志
                 logService.insertLogWhenOperating(log);

        }

        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        logger.info("===============返回===============");
        if(!ObjectUtil.isEmpty(result) ){
            String jsonString = JSONUtil.toJsonStr(result);
            if (jsonString.length() > 10000) {
                logger.info("Response内容:" + "返回内容过大");
            } else {
                logger.info("Response内容:" + jsonString);
            }
        }
        logger.info("请求响应时间:" + time + "ms");
        logger.info("===============返回===============");
        return result;
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
