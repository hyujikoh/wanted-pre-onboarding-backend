package com.ohj.wanted_internship_bakend.app.restapi.member.repository;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;

import java.util.Optional;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUsername(String name);
}
