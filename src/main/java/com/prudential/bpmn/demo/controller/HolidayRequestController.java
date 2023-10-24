package com.prudential.bpmn.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prudential.bpmn.demo.dto.HolidayRequestResult;
import com.prudential.bpmn.demo.entity.Employee;
import com.prudential.bpmn.demo.repository.EmployeeRepository;

@RestController
public class HolidayRequestController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/api/check-holiday-quota")
    public HolidayRequestResult checkHolidayQuota(@RequestParam String userId, @RequestParam int applyDays){
        Optional<Employee> employee = employeeRepository.findById(userId);
        if(employee.isEmpty()) {
            return new HolidayRequestResult(userId, applyDays, "No");
        }
        if(employee.get().getRemainingHolidays() >= applyDays) {
            return new HolidayRequestResult(userId, applyDays, "Yes");
        } else {
            return new HolidayRequestResult(userId, applyDays, "No");
        }
    }

}
