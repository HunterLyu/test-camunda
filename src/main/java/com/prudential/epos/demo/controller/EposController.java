package com.prudential.epos.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.epos.demo.entity.Employee;
import com.prudential.epos.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class EposController {
    public static final ObjectMapper OBJECT_MAPPER = JacksonUtils.enhancedObjectMapper();

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/api/init-db-data")
    public String initDbData() {
        Employee employee = Employee.builder().id("1").name("Mike").remainingHolidays(10).build();
        employeeRepository.save(employee);
        return "OK";
    }


    @GetMapping("/api/hello")
    public String test() {
        return "hello world";
    }


    @GetMapping("/api/customers")
    public List<Employee> getCustomers(@RequestParam(value = "userId", required = false, defaultValue = "1") String userId,
                                       @RequestParam(value = "instanceId", required = false, defaultValue = "1") String instanceId) {

        log.info("request userId: " + userId);
        log.info("request instanceId: " + instanceId);

        List<Employee> customers = new ArrayList<>();
        customers.add(Employee.builder().id("120").name("Zengke").remainingHolidays(10).build());
        employeeRepository.findAll().forEach(c -> customers.add(c));
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
        log.error("send kafka message failed：" + ex.getMessage());

        return null;
    }

    public SendResult<String, Object> onSuccess(SendResult<String, Object> result) {
        log.info("send kafka message success：" + result.getRecordMetadata().topic() + "-"
                + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());

        return result;
    }
}
