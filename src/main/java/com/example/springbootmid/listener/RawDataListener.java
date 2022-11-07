package com.example.springbootmid.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author :Panking
 * @date : 2022/9/28
 */
@Slf4j
@Component
public class RawDataListener {
    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
     *
     * @param record kafka 生产数据
     */
    @KafkaListener(topics = "baorong")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("process:" + record.topic() + record.partition() + record.toString());
    }
}

