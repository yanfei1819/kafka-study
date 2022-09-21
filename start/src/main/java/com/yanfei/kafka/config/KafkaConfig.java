package com.yanfei.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-21
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    /**
     * 消费者配置
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=ylzh password=ylzh;");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        factory.setConcurrency(2);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    /**
     * 生产者配置
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateConfig() {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=ylzh password=ylzh;");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    }
}
