package com.liuchao.myspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RSA加签 验签功能
 * 点击防刷
 * rabbitMq
 * org.yaml.snakeyaml.Yaml 读取yml文件的方法
 * 打印预览的功能
 */
@SpringBootApplication
@MapperScan("com.liuchao.myspringboot.service")
public class MyspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspringbootApplication.class, args);
    }

}
