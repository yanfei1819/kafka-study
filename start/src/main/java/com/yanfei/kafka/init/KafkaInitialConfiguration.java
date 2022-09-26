package com.yanfei.kafka.init;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc:
 * @Author: shiyanfei
 * @Date: 2022-09-19
 */
@Configuration
public class KafkaInitialConfiguration {

    @Bean
    public NewTopic initialTopic() {
        // 参数：topic、分区数、副本数
        // 如果要修改分区数，只需修改配置值重启项目即可
        // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
        return new NewTopic("myTopic",1, (short) 1);
    }
}
