package com.ohj.wanted_internship_bakend.member;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.AlreadyJoinException;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.UserNotExistException;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 * 회원 테스트코드
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@ActiveProfiles("test")
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();
        memberService.join(memberReq);
    }
    @Test
    public void 회원가입_서비스() throws Exception{
        //Given
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("ohj@naver.com")
                .password("12345678")
                .build();
        memberService.join(memberReq);
        Optional<Member> findMember = memberService.findUser(memberReq.getUserEmail());
        assertEquals(memberReq.getUserEmail(), findMember.get().getUserEmail());

    }

    @Test
    public void 중복된정보로_회원가입시_오류처리_서비스() throws Exception{
        //Given
        MemberReq memberReq1 = new MemberReq().builder()
                .userEmail("ohj@naver.com")
                .password("12345678")
                .build();

        MemberReq memberReq2 = new MemberReq().builder()
                .userEmail("ohj@naver.com")
                .password("12345678")
                .build();
        memberService.join(memberReq1);
        assertThrows(AlreadyJoinException.class, () -> memberService.join(memberReq2));
    }

    @Test
    void 로그인_성공_서비스(){
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();

        Member member = memberService.logIn(memberReq);

        assertThat(member.getUserEmail()).isEqualTo(memberReq.getUserEmail());
    }

    @Test
    void 로그인_실패_서비스(){
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678124124")
                .build();

        assertThrows(UserNotExistException.class, () -> memberService.logIn(memberReq));
    }

}
