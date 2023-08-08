package com.ohj.wanted_internship_bakend.app.restapi.member.service;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.AlreadyJoinException;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 서비스 로직
     * @param member
     * @return
     */
    @Override
    public Member join(Member member) {
        if (memberRepository.findByUsername(member.getUsername()).isPresent()) {
            throw new AlreadyJoinException();
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member;
    }

    /**
     * 유저 네임 기준 조회
     * @param name
     * @return
     */
    @Override
    public Optional<Member> findUser(String name) {

        return memberRepository.findByUsername(name);
    }


}
