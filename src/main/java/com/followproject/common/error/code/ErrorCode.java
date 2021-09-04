package com.followproject.common.error.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //COMMON
    INVALID_REQUEST(400,"C004001","잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(404,"COO4002","해당 리소스를 찾을 수 없습니다."),
    FORBIDDEN(403,"C004003","해당 권한이 없습니다."),
    SERVER_ERROR(500,"COO5001","서버에서의 오류입니다."),

    //ACCOUNT
    NOT_FOUND_USER(400,"A004001","해당 계정을 찾을 수 없습니다."),
    INVALID_EMAIL_FORMAT(400,"A004002","이메일 형식으로 입력해주세요."),
    EXIST_EMAIL(400,"A004003","이미 존재하는 이메일입니다.");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
