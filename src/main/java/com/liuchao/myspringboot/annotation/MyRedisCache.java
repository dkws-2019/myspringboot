package com.liuchao.myspringboot.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRedisCache {
    String key() default "";
    Class type();
}
