package com.liuchao.myspringboot.config;

import com.liuchao.myspringboot.annotation.MyRedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class RedisCacheAop {
    @Autowired
    private RedisTemplate redisTemplate;


    @Pointcut("@annotation(com.liuchao.myspringboot.annotation.MyRedisCache)")
    public void annotationPoinCut(){}


    @AfterReturning(pointcut = "annotationPoinCut()",returning = "returnValue")
    public void after(JoinPoint joinPoint,Object returnValue){
        MethodSignature methodSignature=(MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        MyRedisCache annotation = method.getAnnotation(MyRedisCache.class);
        String key = annotation.key();
        redisTemplate.opsForValue().set(key,returnValue);
    }

}
