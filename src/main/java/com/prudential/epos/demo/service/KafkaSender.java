package com.prudential.epos.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class KafkaSender {
    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, Object message) {
        log.info("[++++++++++++++++++ message ：{}]", message);
        kafkaTemplate.send(topic, message.toString());
    }
}
