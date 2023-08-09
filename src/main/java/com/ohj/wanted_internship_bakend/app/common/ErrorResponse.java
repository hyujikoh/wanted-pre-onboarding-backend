package com.ohj.wanted_internship_bakend.app.common;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc: Request 에러가 발생했을 경우의 응답 클래스
 */
@Getter
public class ErrorResponse {

    private final Map<String, String> errorFieldMessage = new HashMap<>();

    public void putErrorMessage(String field, String message) {
        errorFieldMessage.put(field, message);
    }
}
