package com.yanfei.kafka.sasl;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.Properties;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-21
 */
public class SaslProducer {
//    private static final Logger logger = LoggerFactory.getLogger(SendMessageMain.class);
    public static void main(String... args) throws Exception {
        try {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "bms.daosenergy.com:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
            props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
            props.put("sasl.jaas.config",
                    "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"producer\" password=\"producer-sec\";");


            //System.setProperty("java.security.auth.login.config", "D:\workspaceGithub\\kafka_client_jaas.conf"); //配置文件路径

            System.out.println("create KafkaProducer");
            Producer<String, String> producer = new KafkaProducer<String, String>(props);
            String data = "aaa";
            ProducerRecord<String, String> producerRecord = new ProducerRecord(" test-topic", data);

            for (int i = 0; i < 100; i++) {
                System.out.println(i);
                producer.send(producerRecord,
                        new org.apache.kafka.clients.producer.Callback() {
                            @Override
                            public void onCompletion(RecordMetadata metadata, Exception e) {
                                if(e != null) {
                                    System.out.println("onCompletion exception");
                                    e.printStackTrace();
                                }
                                System.out.println("The offset of the record we just sent is: " + metadata);
                            }
                        });
                System.out.println("flush producer");
                producer.flush();
            }

            System.out.println("close producer");
            producer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("when calling kafka output error." + ex.getMessage());
        }
    }
}
