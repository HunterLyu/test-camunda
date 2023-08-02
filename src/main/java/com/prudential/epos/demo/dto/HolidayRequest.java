package com.prudential.epos.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayRequest {
    private Long employeeId;
    private int applyDays;
}
