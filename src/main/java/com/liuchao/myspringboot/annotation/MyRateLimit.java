package com.liuchao.myspringboot.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRateLimit {
    int seconds () default 2;
    int count() default 2;
}
