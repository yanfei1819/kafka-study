package com.yanfei.kafka.handler;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-19
 */
@Component
public class ConsumerAwareListenerErrorHandler {

    // 新建一个异常处理器，用@Bean注入
//    @Bean
//    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
//        return (message, exception, consumer) -> {
//            System.out.println("消费异常："+message.getPayload());
//            return null;
//        };
//    }

    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
//    @KafkaListener(topics = {"topic1"},errorHandler = "consumerAwareErrorHandler")
//    public void onMessage4(ConsumerRecord<?, ?> record) throws Exception {
//        throw new Exception("简单消费-模拟异常");
//    }
//
//    // 批量消费也一样，异常处理器的message.getPayload()也可以拿到各条消息的信息
//    @KafkaListener(topics = "topic1",errorHandler="consumerAwareErrorHandler")
//    public void onMessage5(List<ConsumerRecord<?, ?>> records) throws Exception {
//        System.out.println("批量消费一次...");
//        throw new Exception("批量消费-模拟异常");
//    }
}
