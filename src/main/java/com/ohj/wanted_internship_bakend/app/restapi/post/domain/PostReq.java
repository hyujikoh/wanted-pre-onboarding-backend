package com.ohj.wanted_internship_bakend.app.restapi.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-10
 * Desc : POST DTO
 */

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostReq {
    private long id;
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
}
