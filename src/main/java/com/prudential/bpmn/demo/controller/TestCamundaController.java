package com.prudential.bpmn.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/testCamunda")
public class TestCamundaController {
    public static final ObjectMapper OBJECT_MAPPER = JacksonUtils.enhancedObjectMapper();

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/startProcess")
    public String startProcess(@RequestParam(name = "needApprove", defaultValue = "false") Boolean needApprove) {
        Map<String, Object> param = new HashMap<>();
        param.put("needApprove", needApprove);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test_camunda_01", param);

        String processInstanceId = processInstance.getProcessInstanceId();
        log.info("process started: " + processInstanceId);

        return "OK, " + processInstanceId;
    }

    @GetMapping("/approveByInstanceId")
    public List<String> approveByInstanceId(@RequestParam(name = "instanceId") String instanceId) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instanceId).listPage(0, 100);
        List<String> taskIds = new ArrayList<>();
        if (tasks != null) {
            tasks.forEach(t->{
                taskService.complete(t.getId());
                taskIds.add(t.getId());
            });
        }

        return taskIds;
    }


    @GetMapping("/hello")
    public String test() {
        return "hello world";
    }

    @GetMapping("/listHistoryInstance")
    public List<HistoricProcessInstance> listHistoryInstance() {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery.finished().orderByProcessInstanceStartTime().desc().listPage(0, 10);

        return historicProcessInstances;
    }


}
