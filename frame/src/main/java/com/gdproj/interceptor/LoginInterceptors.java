package com.gdproj.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdproj.exception.TokenExpiredException;
import com.gdproj.result.ResponseResult;
import com.gdproj.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptors implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptors.class);
 
 
    /**
     * 将java对象变为Json字符串
     * @param obj
     * @return Json字符串
     * @throws JsonProcessingException
     */
    public static String objToJson(Object obj) throws JsonProcessingException {
        if(obj == null){
            return "";
        }
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
 
    /**
     * 重构response，但返回前端
     * @param response
     * @param errorMsg
     */
    private void returnResponse(HttpServletResponse response, String errorMsg){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try (PrintWriter out = response.getWriter()) {
            out.write(objToJson(ResponseResult.errorResult(10000,errorMsg)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 在请求处理之前进行调用(Controller方法调用之前)
     * 若返回true请求将会继续执行后面的操作
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        ResponseResult<Object> responseResult = new ResponseResult<>();

        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (!StringUtils.hasLength(token)) {
            //设置响应头（告知浏览器：响应的数据类型为json、响应的数据编码表为utf-8）
            response.setContentType("application/json;charset=utf-8");
            // 将结果返回给前端
            throw new TokenExpiredException(515,"用户未登录");
            // 拦截当前请求
//            return false;
        }
            // 校验当前的JWT令牌是否错误
            if(!JwtUtils.checkToken(token)){
                //设置响应头（告知浏览器：响应的数据类型为json、响应的数据编码表为utf-8）
                response.setContentType("application/json;charset=utf-8");
                responseResult.error(10010,"token解析异常");
                throw new TokenExpiredException(515,"token异常或过期");
                // 拦截当前请求
//                return false;
            }
        return true;
    }
 
    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了拦截器的postHandle方法");
    }
 
    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除线程变量
 
    }
 
}