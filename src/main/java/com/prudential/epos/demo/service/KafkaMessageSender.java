package com.prudential.epos.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageSender {
    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        log.info("[++++++++++++++++++ message ：{}]", message);
        kafkaTemplate.send(topic, message);
    }
}
