package com.liuchao.myspringboot.interceptor;

import com.liuchao.myspringboot.annotation.MyRateLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * 点击防刷
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

        @Autowired
        private StringRedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            if(handlerMethod.hasMethodAnnotation(MyRateLimit.class)) {
                MyRateLimit myRateLimit = handlerMethod.getMethodAnnotation(MyRateLimit.class);
                int count = myRateLimit.count() - 1;
                int seconds = myRateLimit.seconds();
                Object buttonLimit = redisTemplate.opsForValue().get("buttonLimit");
                if (buttonLimit == null) {
                    redisTemplate.opsForValue().set("buttonLimit", "1");
                    redisTemplate.expire("buttonLimit", seconds, TimeUnit.SECONDS);

                } else if ((Integer.parseInt(buttonLimit.toString())) <= count) {
                    redisTemplate.opsForValue().increment("buttonLimit", 1);

                } else {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("按扭点击太频繁");
                    return false;

                }
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
}
