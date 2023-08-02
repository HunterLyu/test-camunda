package com.prudential.epos.demo.controller;

import com.prudential.epos.demo.dto.HolidayRequest;
import com.prudential.epos.demo.entity.Employee;
import com.prudential.epos.demo.repository.EmployeeRepository;
import com.prudential.epos.demo.service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolidayRequestController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/api/holiday-quota")
    public String checkHolidayQuota(@RequestParam String userId, @RequestParam int applyDays){
        Employee employee = employeeRepository.findById(1L).get();
        if(employee.getRemainingHolidays() >= applyDays) {
            return "Yes";
        } else {
            return "No";
        }
    }

}
