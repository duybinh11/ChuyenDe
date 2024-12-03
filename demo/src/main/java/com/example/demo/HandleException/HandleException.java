package com.example.demo.HandleException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.Response.ApiResponse;

@ControllerAdvice
public class HandleException {
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntime(Exception exception) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(false);
        apiResponse.setCode(404);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // @SuppressWarnings("rawtypes")
    // @ExceptionHandler(value = Exception.class)
    // ResponseEntity<ApiResponse> handleE(Exception exception) {
    // ApiResponse<String> apiResponse = new ApiResponse<>();
    // apiResponse.setStatus(false);
    // apiResponse.setCode(404);
    // apiResponse.setMessage(exception.getMessage());
    // return ResponseEntity.badRequest().body(apiResponse);
    // }

    // @SuppressWarnings({ "rawtypes", "null" })
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity
    // handleValidationExceptions(MethodArgumentNotValidException ex) {
    // return
    // ResponseEntity.badRequest().body(ex.getFieldError().getDefaultMessage());
    // }
}