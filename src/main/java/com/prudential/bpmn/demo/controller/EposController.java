package com.prudential.bpmn.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.bpmn.demo.entity.Employee;
import com.prudential.bpmn.demo.repository.EmployeeRepository;

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

    @GetMapping("/api/testClient")
    public String testClient() {

        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("127.0.0.1")
                .setDatabase("flowable")
                .setUser("admin")
                .setPassword("test");

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        SqlClient client = PgPool.client(connectOptions, poolOptions);

        String query = "SELECT content FROM test WHERE content->>'info' = $1";

        String updateQuery = "update test set content = public.jsonb_deep_set(content, '{info}', $1) WHERE " +
                "content->>'name' = $2";


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());;



//        query = String.format(query, localDateTime);

        log.info(updateQuery);

        AtomicReference<String> jResult = new AtomicReference<>();


        client
                .preparedQuery(updateQuery)
//                .execute(Tuple.of(JsonObject.of("address", "ffe", "info", "www"), "lisi"))
                .execute(Tuple.of("222","lisi"))
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> result = ar.result();
                        System.out.println("Got " + result.size() + " rows ");
                        result.forEach(row->{
                            jResult.set(row.toJson().toString());
                            log.info("Row content: " + jResult.get());
                        });
                    } else {
                        log.error("Failure: " + ar.cause().getMessage());
                    }

                    // Now close the pool
                    client.close();
                });

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            log.error("Exception thrown.", e);
            return "failed with exception.";
        }


        return jResult.get();
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
