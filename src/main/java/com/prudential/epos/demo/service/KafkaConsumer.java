package com.prudential.epos.demo.service;

import com.prudential.epos.demo.Constant;
import com.prudential.epos.demo.dto.HolidayRequest;
import com.prudential.epos.demo.dto.HolidayRequestResult;
import com.prudential.epos.demo.entity.Employee;
import com.prudential.epos.demo.repository.EmployeeRepository;
import com.prudential.epos.demo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private KafkaMessageSender kafkaSender;

    @KafkaListener(topics = {Constant.TOPIC_BOSS_MSG_BOX})
    public void listen(ConsumerRecord<?, ?> record) {
        Object msg = record.value();
        log.info("+++++++++++++++++ message = {}", msg);
        HolidayRequest request = (HolidayRequest) msg;
        Employee employee = employeeRepository.findById(1L).get();
        employee.setRemainingHolidays(employee.getRemainingHolidays() - request.getApplyDays());
        employeeRepository.save(employee);
        kafkaSender.sendMessage(Constant.TOPIC_BOSS_MSG_FEEDBACK, JsonUtil.toJson(new HolidayRequestResult("Yes")));
    }

}