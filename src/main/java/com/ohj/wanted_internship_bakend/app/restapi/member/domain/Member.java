package com.ohj.wanted_internship_bakend.app.restapi.member.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userEmail;

    @JsonIgnore
    private String password;

    @Transient
    private String accessToken;

    public void setPassword(String encode) {
        this.password = encode;
    }

    public void setAccessToken(String jwt) {
        this.accessToken = jwt;
    }


}
