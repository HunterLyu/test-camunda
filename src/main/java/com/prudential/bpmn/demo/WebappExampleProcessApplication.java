package com.prudential.bpmn.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappExampleProcessApplication {

    public static void main(String[] args) {
        System.out.println("Server started...");
        SpringApplication.run(WebappExampleProcessApplication.class, args);
    }

}
