package com.gymbooking.timetable_service.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandarizedApiExceptionResponse {
    private String type = "error";
    private String title;
    private String code;
    private String detail;
    private String instance;
}
