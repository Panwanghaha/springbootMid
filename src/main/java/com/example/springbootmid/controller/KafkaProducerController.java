package com.example.springbootmid.controller;

import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :Panking
 * @date : 2022/9/28
 */
@RestController
public class KafkaProducerController {
    @Resource
    private KafkaTemplate kafkaTemplate;

    /**
     * kafka 生产队列
     */
    @RequestMapping(value = "/kafka/producer", method = RequestMethod.GET)
    public void consume(HttpServletRequest request, HttpServletResponse response) {
        for (int i = 0; i < 10; i++) {
            ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("kafkaMessage", "hello");
            future.addCallback(result -> {
                System.out.println("发送消息成功");
            }, (KafkaFailureCallback<Integer, String>) ex -> {
                System.out.println("发送消息失败");
            });
        }
    }
}