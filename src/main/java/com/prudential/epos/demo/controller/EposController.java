package com.prudential.epos.demo.controller;

import com.prudential.epos.demo.entity.Employee;
import com.prudential.epos.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EposController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/api/init-db-data")
    public String initDbData(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Mike");
        employee.setRemainingHolidays(10);
        employeeRepository.save(employee);
        return "OK";
    }
}
