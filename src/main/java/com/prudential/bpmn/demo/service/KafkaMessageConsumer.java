package com.prudential.bpmn.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    protected Logger log = LoggerFactory.getLogger(getClass());


    @KafkaListener(topics = {"task1FinishTopic", "task1SendTopic", "task2FinishTopic", "task2SendTopic",
            "flow3SendTopic1", "flow3FinishTopic1", "flow3SendTopic2"})
    public void onMessage1(ConsumerRecord<?, ?> record) {
        log.info("consume message of:：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }
}
