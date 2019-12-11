package com.liuchao.myspringboot.decorator;

import java.sql.SQLOutput;

/**
 * 被装饰者实现类
 */
public class WuShi implements Person {
    @Override
    public String doWare() {
       return "武士";
    }
}
