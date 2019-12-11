package com.liuchao.myspringboot.pay;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

@Component
public class PayResource {
    private List<PayService> list=new ArrayList<PayService>();


    public void registService(PayService payService){
        list.add(payService);
    }

    public PayService getPayMethod(String name){
        for(PayService payService:list){
            String payName = payService.getName();
            if(name.equals(payName)){
                return payService;
            }

        }
        return null;
    }

    public static void main(String[] args) {
        ServiceLoader<PayService> loader = ServiceLoader.load(PayService.class);
        Iterator<PayService> iterator = loader.iterator();
        while(iterator.hasNext()){
            PayService next = iterator.next();
            System.out.println(next);

        }
    }
}
