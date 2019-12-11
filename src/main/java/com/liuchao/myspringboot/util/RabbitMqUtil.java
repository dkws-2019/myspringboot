package com.liuchao.myspringboot.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMqUtil {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private CreatStaticHtml creatStaticHtml;

    /**
     * 发送队列到mq  可以根据路由的名字将队列发送到对应的 queue
     * @param exchangeName 交换器的名子
     * @param routeName 路由的名字
     * @param context 内容
     */
    public void send(String exchangeName,String routeName,String context) {
        //这个context 也可以是个map
        this.rabbitTemplate.convertAndSend(exchangeName, routeName, context);
    }

    /**
     * 这个是 消费端 只要 名为item.insert 或item.update 的队列里面有数据就会被这个方法消费
     * @param id
     */
    @RabbitListener(queues = {"item.insert","item.update"})
    //这个参数也可以是个map
    public void receiveMessageUpOrIn(String id){
        System.out.println("添加或修改的传过来的id: "+id);
    }

    @RabbitListener(queues = {"item.delete"})
    public void receiveMessageDel(String id){
        System.out.println("删除时候的id： " + id);
    }

    @RabbitListener(queues = {"createHtmlQueue"})
    public void receiveMessageCreateHtml(String userId) throws IOException {
        creatStaticHtml.createHtml(userId);
       // System.out.println("删除时候的id： " + id);
    }

}
