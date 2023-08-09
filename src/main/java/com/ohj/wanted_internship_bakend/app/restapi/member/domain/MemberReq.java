package com.ohj.wanted_internship_bakend.app.restapi.member.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-09
 * Desc : member  DTO
 */
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReq {

    @Email(message = "Invalid email format")
    @NotNull
    private String userEmail;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public void setPassword(String encrypt) {
        this.password = encrypt;
    }
}
