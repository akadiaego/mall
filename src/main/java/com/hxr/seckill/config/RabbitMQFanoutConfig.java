//package com.hxr.seckill.config;
//
//
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitMQFanoutConfig {
////    private static final String QUEUE01 = "queue_fanout01";
////    private static final String QUEUE02 = "queue_fanout02";
////    private static final String EXCHANGE = "fanoutExchange";
////    @Bean
////    public Queue queue(){
////        return new Queue("queue",true);
////    }
////    @Bean
////    public Queue queue01(){
////        return new Queue(QUEUE01);
////    }
////    @Bean
////    public Queue queue02(){
////        return new Queue(QUEUE02);
////    }
////    @Bean
////    public FanoutExchange fanoutExchange(){
////        //Fanout模式
////        //又叫广播模式，类似于一个微信公众号，当公众号发布消息的时候所有关注该公众号的用户都能收到消息，FanoutExchange的功能就类似与公众号。
////        return new FanoutExchange(EXCHANGE);
////    }
////    @Bean
////    public Binding binding01(){
////        return BindingBuilder.bind(queue01()).to(fanoutExchange());
////    }
////    @Bean
////    public Binding binding02(){
////        return BindingBuilder.bind(queue02()).to(fanoutExchange());
////    }
//
//}
