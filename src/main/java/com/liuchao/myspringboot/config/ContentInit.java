package com.liuchao.myspringboot.config;

import com.liuchao.myspringboot.pay.PayResource;
import com.liuchao.myspringboot.pay.PayService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 启动的时候把接口实现类注入
 */
@Component
public class ContentInit implements ApplicationContextAware {
    @Autowired
    private PayResource payResource;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PayService> beansOfType = applicationContext.getBeansOfType(PayService.class);
        List<String> keyList=new ArrayList<String>(beansOfType.keySet());
        for(String key:keyList){
            PayService payService = beansOfType.get(key);
            String name = payService.getName();
            payResource.registService(payService);
        }
    }
}
