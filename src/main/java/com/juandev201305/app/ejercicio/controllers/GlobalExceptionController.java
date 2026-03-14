package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiReponseStatusDto> handlerRuntimeException(RuntimeException runtimeException){
        ApiReponseStatusDto response = new ApiReponseStatusDto();
        response.setSuccess(false);
        response.setMessage(runtimeException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
