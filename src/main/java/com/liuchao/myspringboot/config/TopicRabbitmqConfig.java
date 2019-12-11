package com.liuchao.myspringboot.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq是生产者访问交换器，交换器通过路由访问对应的队列
 */
@Configuration
public class TopicRabbitmqConfig {
    //这个是queue队列的名
    public static final String insertQueue="item.insert";
    public static final String updateQueue="item.update";
    public static final String deleteQueue="item.delete";
    public static final String createHtmlQueue="createHtmlQueue";
    //这个是交换器的名
    public static final String topicExchange="topicExchange";

    /**
     * 创建名为item.insert的队列
     * @return
     */
    @Bean
    public Queue getInsertQueue(){

        return new Queue(TopicRabbitmqConfig.insertQueue);
    }
    @Bean
    public Queue geCreateHtmlQueue(){

        return new Queue(TopicRabbitmqConfig.createHtmlQueue);
    }
    @Bean
    public Queue getUpdateQueue(){
        return new Queue(TopicRabbitmqConfig.updateQueue);
    }
    @Bean
    public Queue getDeleteQueue(){
        return new Queue(TopicRabbitmqConfig.deleteQueue);
    }

    /**
     * 创建名为topicExchange的交换器
     * @return
     */
    @Bean
    public TopicExchange getTopicExchange(){

        return new TopicExchange(TopicRabbitmqConfig.topicExchange);
    }

    /**
     * 把队列和交换器绑定并指定路由
     * @param geCreateHtmlQueue
     * @param getTopicExchange
     * @return
     */
    @Bean
    public Binding bindInserQueueTopicExchange(Queue geCreateHtmlQueue,TopicExchange getTopicExchange){
            return BindingBuilder.bind(geCreateHtmlQueue).to(getTopicExchange).with("createHtmlQueue");
    }

    @Bean
    public Binding bindCreateQueueTopicExchange(Queue getInsertQueue,TopicExchange getTopicExchange){
        return BindingBuilder.bind(getInsertQueue).to(getTopicExchange).with("item.insert");
    }

    @Bean
    public Binding bindUpdateQueueTopicExchange(Queue getUpdateQueue,TopicExchange getTopicExchange){
        return BindingBuilder.bind(getUpdateQueue).to(getTopicExchange).with("item.update");
    }

    @Bean
    public Binding bindDeleteQueueTopicExchange(Queue getDeleteQueue,TopicExchange getTopicExchange){
        return BindingBuilder.bind(getDeleteQueue).to(getTopicExchange).with("item.delete");
    }
}
