package com.gymbooking.booking.dto;

import lombok.Data;

@Data
public class ScheduledClassResponse {
    private Long id;
    private String startTime;
    private String endTime;
    private Integer spotsAvailable;
    private String className;
    private String instructorName;
}
