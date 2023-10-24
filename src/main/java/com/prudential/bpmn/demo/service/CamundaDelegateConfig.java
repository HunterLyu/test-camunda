package com.prudential.bpmn.demo.service;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaDelegateConfig {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Bean("autoTask01")
    JavaDelegate autoTask01(){
        return execution->{

            log.info("task ID: " + execution.getId());
        };
    }
}
