package com.prudential.epos.demo.controller;

import com.prudential.epos.demo.entity.Customer;
import com.prudential.epos.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EposController {
    @Autowired
    private CustomerRepository customerRepository;
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
}
