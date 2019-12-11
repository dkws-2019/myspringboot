package com.liuchao.myspringboot.util;

import com.liuchao.myspringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存页面 将魔板页面写入到nginx 的html目录下面
 * 然后nginx 配置如果页面存在就直接访问nginx下面的页面如果 不存就反向代理到生成页面的方法
 *
 */
@Component
public class CreatStaticHtml {
    @Autowired
    private TemplateEngine templateEngine;

    public void createHtml(String userId) throws IOException {
        User user1=new User();
        user1.setId(1);
        user1.setName("zhangsan");

        User user2=new User();
        user2.setId(2);
        user2.setName("lisi");

        User user3=new User();
        user3.setId(3);
        user3.setName("wang wu");
        Map<String,User> map=new HashMap<String,User>();
        map.put("1",user1);
        map.put("2",user2);
        map.put("3",user3);

        Context context=new Context();
       context.setVariable("user",map.get(userId));

        File file=new File("E:\\nginx-1.15.0\\html\\item",userId+".html");
        if(file.exists()){
            file.delete();
        }
        FileWriter fileWriter=new FileWriter(file);
        templateEngine.process("index", context, fileWriter);
        fileWriter.flush();
        fileWriter.close();

    }
}
