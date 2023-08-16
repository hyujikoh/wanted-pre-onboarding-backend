package com.ohj.wanted_internship_bakend.app.restapi.member.repository;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;

import java.util.Collection;
import java.util.Optional;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUserEmail(String name);

    Optional<Member> findByUserEmailAndPassword(String userEmail, String password);

    Optional<Member> findById(long id);
}
