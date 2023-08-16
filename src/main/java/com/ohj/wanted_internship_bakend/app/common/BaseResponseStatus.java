package com.ohj.wanted_internship_bakend.app.common;

import lombok.Getter;

/**
 Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc : 에러 코드가 정의되어 있는 클래스
 */
@Getter
public enum BaseResponseStatus {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    REQUEST_ERROR(false, 4000, "입력값이 양식에 맞는지 확인해주세요."),
    EMPTY_JWT(false, 4010, "JWT를 입력해주세요."),
    INVALID_JWT(false, 4011, "유효하지 않은 JWT 입니다."),
    WRONG_AUTH(false, 4012, "유효하지 않은 사용자 입니다."),;
    ;

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
