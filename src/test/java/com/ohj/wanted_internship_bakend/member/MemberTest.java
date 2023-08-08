package com.ohj.wanted_internship_bakend.member;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * 회원 테스트코드
 */

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception{
        //Given
        Member member = new Member().builder()
                .name("oh")
                .password("1234")
                .build();
        memberService.join(member);

        Optional<Member> isExistUser = memberService.findUser(member.getName());
        if(isExistUser.isPresent()) System.out.println("회원 등록 완료");;
    }

}
