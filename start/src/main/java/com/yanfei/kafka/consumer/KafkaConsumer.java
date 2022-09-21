package com.yanfei.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-19
 */

@Component
public class KafkaConsumer {

    // 消费监听
    @KafkaListener(topics = {"test"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


    /**
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     **/
    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
            @TopicPartition(topic = "test", partitions = { "0" }),
            @TopicPartition(topic = "myTopic", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "1"))
    })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        System.out.println("topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
    }


    /**
     * @Title 消息转发
     * @Description 从topic1接收到的消息经过处理后转发到topic2
     * @Author long.yuan
     * @Date 2020/3/23 22:15
     * @Param [record]
     * @return void
     **/
    @KafkaListener(topics = {"topic1"})
    @SendTo("topic2")
    public String onMessage7(ConsumerRecord<?, ?> record) {
        return record.value()+"-forward message";
    }
}