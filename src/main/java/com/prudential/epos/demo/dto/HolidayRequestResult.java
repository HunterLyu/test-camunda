package com.prudential.epos.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HolidayRequestResult {
    private String userId;
    private int applyDays;
    private String status; // Yes/No
    public String getHolidayApproved() {
        return status;
    }
}
