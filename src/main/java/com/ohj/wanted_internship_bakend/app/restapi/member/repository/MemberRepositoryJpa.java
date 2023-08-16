package com.ohj.wanted_internship_bakend.app.restapi.member.repository;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc : SpringDataJpaRepo 클래스
 */
@Repository
public interface MemberRepositoryJpa extends JpaRepository<Member, Long>, MemberRepository {
}
