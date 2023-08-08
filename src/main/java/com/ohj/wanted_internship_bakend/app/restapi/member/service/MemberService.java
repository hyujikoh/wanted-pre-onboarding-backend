package com.ohj.wanted_internship_bakend.app.restapi.member.service;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;

import java.util.Optional;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
public interface MemberService {
    Member join(Member member);

    Optional<Member> findUser(String name);
}
