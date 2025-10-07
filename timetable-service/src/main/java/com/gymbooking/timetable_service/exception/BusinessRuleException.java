package com.gymbooking.timetable_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessRuleException extends Exception {
    private final String code;
    private final HttpStatus httpStatus;

    public BusinessRuleException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
