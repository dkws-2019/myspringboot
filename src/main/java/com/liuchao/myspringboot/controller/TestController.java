package com.liuchao.myspringboot.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.liuchao.myspringboot.annotation.MyRateLimit;
import com.liuchao.myspringboot.annotation.MyRedisCache;
import com.liuchao.myspringboot.config.TopicRabbitmqConfig;
import com.liuchao.myspringboot.entity.User;
import com.liuchao.myspringboot.pay.PayResource;
import com.liuchao.myspringboot.pay.PayService;
import com.liuchao.myspringboot.service.IUserService;
import com.liuchao.myspringboot.util.CreatStaticHtml;
import com.liuchao.myspringboot.util.RabbitMqUtil;
import com.liuchao.myspringboot.util.YmlRead;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.yaml.snakeyaml.Yaml;


import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PayResource payResource;

    @Autowired
    private User getUser1;

    @Resource
    private IUserService iUserService;

    @Autowired
    private CreatStaticHtml creatStaticHtml;


    @Autowired
    private RabbitMqUtil rabbitMqUtil;

    @RequestMapping("/validateTest")
    @ResponseBody
    public Map  validateTest(@Valid  User user, BindingResult bindingResult){
        Map<String,Object> map=new HashMap<String,Object>();
        if(bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            map.put("errorMessage",defaultMessage);
            return map;
        }
        map.put("user",user);
            return map;
    }

    /**
     * 页面静态化的方法
     * 访问nginx下面的页面如果页面不存反向代理到这个方法生成页面放到nginx下面 第二次访问的时候
     * nginx下面就有这个文件了所以就直接访问那个页面了不就不会再走这个方法了
     * @param userId
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/item/{userId}.html")
    public String hello(@PathVariable("userId")String userId, Model model) throws IOException {
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
        model.addAttribute("user",map.get(userId));
        creatStaticHtml.createHtml(userId);
        rabbitMqUtil.send(TopicRabbitmqConfig.topicExchange,"createHtmlQueue",userId);
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "测试成功";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user(){
       // AlipaySignature.rsaSign();
        System.out.println(getUser1);

        return "测试成功";
    }

    @RequestMapping("/rabbitMqTest")
    @ResponseBody
    public String rabbitMqTest(@RequestParam("queueName")String queueName,
                               @RequestParam("id")String id){
        rabbitMqUtil.send(TopicRabbitmqConfig.topicExchange,queueName,id);
        return "发送队列成功";
    }

    @RequestMapping("/button/text")
    @ResponseBody
    @MyRateLimit(seconds=10,count = 2)//点击防刷新 这就是10秒
    public String  buttonLimit(){
        return "index";
    }

   /* @RequestMapping("/item/{id}.html")
    public String item(@PathVariable("id") String id, Model model) throws IOException {
        User user = iUserService.findByid(id);
        Context context=new Context();
        context.setVariable("user",user);

        File file=new File("E:\\nginx-1.15.0\\html\\item",user.getId()+".html");
        if(file.exists()){
            file.delete();
        }
        FileWriter fileWriter=new FileWriter(file);
        templateEngine.process("index", context, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        model.addAttribute("user",user);
        return "index";

    }*/

    /**
     * 用策略模式来替代if else
     * @param payName
     * @return
     */
   @RequestMapping("clmsTest")
    public String clmsTest(@RequestParam("payName")String payName){
       PayService payMethod = payResource.getPayMethod(payName);
       String s = payMethod.payMethod();
       return s;

   }

   @RequestMapping("/getUser")
    @ResponseBody
  // @MyRedisCache(key="userId",type=User.class)//自定义缓存 读数据的时候用的拦截器，返回值放入redis的用的是aop
    public User getUser(@RequestParam("userId")String userId){
       User byid = iUserService.findByid(userId);
      // InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("application.yml");
       String value = YmlRead.getValue("spring.application.name");
       System.out.println(value);
       return byid;
   }

   @RequestMapping("/getAllUser")
    @ResponseBody
    public List<User> getAllUser(){
       List<User> all = iUserService.findAll();
       return all;
   }
    @RequestMapping("/getUserById")
    @ResponseBody
    public User getUserById(@RequestParam("userId")String userId){
        User user = iUserService.findByid(userId);
        return user;
    }
}
