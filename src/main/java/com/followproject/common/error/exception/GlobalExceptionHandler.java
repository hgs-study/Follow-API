package com.followproject.common.error.exception;

import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> BusinessException(BusinessException e){
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        ErrorCode errorCode = e.getErrorCode();
        errorResponse.setMessage(errorCode.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        errorResponse.setStatus(400);
        errorResponse.setCode("");

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
