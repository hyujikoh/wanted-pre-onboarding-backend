package com.ohj.wanted_internship_bakend.app.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc : API의 기본 Reponse로 사용되는 클래스
 */

@ToString(exclude = "errors")
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    private T result;
    private List<String> errors;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this(BaseResponseStatus.SUCCESS.isSuccess(), BaseResponseStatus.SUCCESS.getMessage(), BaseResponseStatus.SUCCESS.getCode(), result, null);
    }

    // 로직에서 예외 발생시
    public BaseResponse(BaseResponseStatus status) {
        this(null, status);
    }

    // 입력에서 예외 발생시
    public BaseResponse(List<String> errors, BaseResponseStatus status) {
        this(status.isSuccess(), status.getMessage(), status.getCode(), null, errors);
    }
}
