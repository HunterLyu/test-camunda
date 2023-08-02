package com.prudential.epos.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.epos.demo.entity.Customer;
import com.prudential.epos.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class EposController {
    public static final ObjectMapper OBJECT_MAPPER = JacksonUtils.enhancedObjectMapper();

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @GetMapping("/api/hello")
    public String test(){
        return "hello world";
    }



    @GetMapping("/api/customers")
    public List<Customer> getCustomers(){
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(c -> customers.add(c));
        return customers;
    }

    @GetMapping("/kafka/send")
    @ResponseBody
    public String sendMessage1(@RequestParam(value = "userId", required = false, defaultValue = "1") String userId
            , @RequestParam(value = "topic") String topic) throws JsonProcessingException, ExecutionException, InterruptedException {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        String kafkaMsg = OBJECT_MAPPER.writeValueAsString(param);

        CompletableFuture<Void> result = kafkaTemplate.send(topic, kafkaMsg)
                .thenAccept(this::onSuccess)
                .exceptionally(this::onFailure);


        return result.toString();
    }

    public Void onFailure(Throwable ex) {
        System.out.println("发送消息失败：" + ex.getMessage());

        return null;
    }

    public SendResult<String, Object> onSuccess(SendResult<String, Object> result) {
        System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());

        return result;
    }
}
