package com.followproject.common.response.util;

import com.followproject.common.response.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static ResponseEntity<ResponseDto> success(HttpStatus status, String data){
        return ResponseEntity.status(status)
                             .body(new ResponseDto(status, data));
    }
}
