package com.liuchao.myspringboot.config;

import com.liuchao.myspringboot.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class SpringIocTestConfig {

    @Bean
    public User getUser1(){
        User user=new User();
        user.setId(1);
        user.setName("zhangsan");
        return user;
    }

    @Bean
    public User getUser2(){
        User user=new User();
        user.setId(2);
        user.setName("lishi");
        return user;
    }

  /*  @Bean
    public TemplateEngine getTemplateEngine(){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");  //模板文件的所在目录
        resolver.setSuffix(".html");       //模板文件后缀
        //创建模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        //将加载器放入模板引擎
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }*/
}
