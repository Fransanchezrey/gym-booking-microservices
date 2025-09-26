package com.gymbooking.classcatalog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.gymbooking.classcatalog.common.StandarizedApiExceptionResponse;

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
