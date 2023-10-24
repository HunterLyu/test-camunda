package com.prudential.bpmn.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayRequest {
    private String userId;
    private int applyDays;
}
