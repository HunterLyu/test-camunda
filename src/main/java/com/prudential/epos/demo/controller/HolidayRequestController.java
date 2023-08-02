package com.prudential.epos.demo.controller;

import com.prudential.epos.demo.dto.HolidayRequestResult;
import com.prudential.epos.demo.entity.Employee;
import com.prudential.epos.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolidayRequestController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/api/holiday-quota")
    public HolidayRequestResult checkHolidayQuota(@RequestParam String userId, @RequestParam int applyDays){
        Employee employee = employeeRepository.findById(1L).get();
        if(employee.getRemainingHolidays() >= applyDays) {
            return new HolidayRequestResult("Yes");
        } else {
            return new HolidayRequestResult("No");
        }
    }

}
