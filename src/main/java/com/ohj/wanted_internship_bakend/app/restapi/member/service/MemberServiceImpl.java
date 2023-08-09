package com.ohj.wanted_internship_bakend.app.restapi.member.service;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.AlreadyJoinException;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.util.JwtManager;
import com.ohj.wanted_internship_bakend.app.util.SHA256;
import lombok.RequiredArgsConstructor;
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

    /**
     * 회원가입 서비스 로직
     * @param memberReq
     * @return
     */
    @Override
    public Member join(MemberReq memberReq) {
        if (memberRepository.findByUserEmail(memberReq.getUserEmail()).isPresent()) {
            throw new AlreadyJoinException();
        }

        memberReq.setPassword(SHA256.encrypt(memberReq.getPassword()));
        Member member = reqToBuilder(memberReq);
        memberRepository.save(member);
        generateAccessToken(member);
        return member;
    }

    /**
     * Member DTO to Member Object
     * @param memberReq
     * @return
     */
    private Member reqToBuilder(MemberReq memberReq) {
        Member member = new Member().builder()
                .userEmail(memberReq.getUserEmail())
                .password(memberReq.getPassword())
                .build();

        return member;
    }

    /**
     * jwt 토큰 생성 메소드
     * @param member
     */
    private void generateAccessToken(Member member) {
        member.setAccessToken(JwtManager.createJwt(member));
    }

    /**
     * 유저 네임 기준 조회
     * @param name
     * @return
     */
    @Override
    public Optional<Member> findUser(String name) {
        return memberRepository.findByUserEmail(name);
    }


}
