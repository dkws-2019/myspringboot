package com.liuchao.myspringboot.interceptor;

import com.liuchao.myspringboot.annotation.MyRedisCache;
import com.liuchao.myspringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class MyRedisCacheInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod) handler;
           if(handlerMethod.hasMethodAnnotation(MyRedisCache.class)){
               MyRedisCache myRedisCache = handlerMethod.getMethodAnnotation(MyRedisCache.class);
               String key = myRedisCache.key();
               Class type = myRedisCache.type();
             String str=  redisTemplate.opsForValue().get(key);

               if(null==str || "".equals(str)){
                  return true;
              }
               response.setCharacterEncoding("utf-8");
               response.setContentType("text/html;charset=UTF-8");
               PrintWriter writer = response.getWriter();
               writer.write(str);
               writer.flush();
               writer.close();
               return false;
           }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    public static  <T> T get(Class<T> clz,Object o){
        if(clz.isInstance(o)){
            return clz.cast(o);
        }
        return null;
    }


}
