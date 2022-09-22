package com.yanfei.kafka.sasl;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-21
 */
public class SaslConsumer {
    public static void main(String... args) throws Exception {
        try {
            Properties props = new Properties();
            props.put("bootstrap.servers", "192.168.0.23:9092");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

//            props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "300000");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
            props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
            props.put("sasl.jaas.config",
                    "org.apache.kafka.common.security.plain.PlainLoginModule required username=ylzh password=ylzh;");

            System.out.println("create KafkaConsumer");
            System.out.println("receive data");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(" test_scram"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                System.out.println("receive data01");
                for (ConsumerRecord<String, String> record: records) {
                    System.out.printf("offset = %d, key= %s , value = %s\n", record.offset(), record.key(), record.value());
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("when calling kafka output error." + ex.getMessage());
        }
    }
}
