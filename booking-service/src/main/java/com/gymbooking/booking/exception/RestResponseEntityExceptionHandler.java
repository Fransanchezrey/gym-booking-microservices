package com.gymbooking.booking.exception;

import com.gymbooking.booking.common.StandarizedApiExceptionResponse;
import com.gymbooking.booking.exception.BusinessRuleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleBusinessRuleException(BusinessRuleException ex, WebRequest request){
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse(
                "error",
                "Business Error",
                ex.getCode(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

}
