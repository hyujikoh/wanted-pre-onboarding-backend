package com.ohj.wanted_internship_bakend.member;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.AlreadyJoinException;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
                .username("oh")
                .password("12345678")
                .build();
        memberService.join(member);
        Optional<Member> findMember = memberService.findUser(member.getUsername());
        assertEquals(member.getUsername(), findMember.get().getUsername());

    }

    @Test
    public void 중복된정보로_회원가입시_오류처리() throws Exception{
        //Given
        Member member1 = new Member().builder()
                .username("oh")
                .password("1234")
                .build();

        Member member2 = new Member().builder()
                .username("oh")
                .password("1234")
                .build();
        memberService.join(member1);
        assertThrows(AlreadyJoinException.class, () -> memberService.join(member2));
    }


    @Test
    public void jwt_기반_로그인() throws Exception {}

}
