package com.ohj.wanted_internship_bakend.app.restapi.post.repository;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * Desc : SpringDataJpaRepo 클래스
 */
@Repository
public interface PostRepositoryJpa extends JpaRepository<Member, Long>, PostRepository {
}
