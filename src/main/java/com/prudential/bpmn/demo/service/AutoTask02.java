package com.prudential.bpmn.demo.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AutoTask02 implements JavaDelegate {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("task ID: " + delegateExecution.getId());
    }
}
