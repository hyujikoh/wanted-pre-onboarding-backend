package com.ohj.wanted_internship_bakend.app.restapi.member.repository;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc : SpringDataJpaRepo 클래스
 */
public interface MemberRepositoryJpa extends JpaRepository<Member, Long>, MemberRepository {
}
