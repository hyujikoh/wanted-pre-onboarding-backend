package com.ohj.wanted_internship_bakend.member;

import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.exception.AlreadyJoinException;
import com.ohj.wanted_internship_bakend.app.restapi.member.repository.MemberRepository;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
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
public class MemberTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //Given
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh")
                .password("12345678")
                .build();
        memberService.join(memberReq);
        Optional<Member> findMember = memberService.findUser(memberReq.getUserEmail());
        assertEquals(memberReq.getUserEmail(), findMember.get().getUserEmail());

    }

    @Test
    public void 중복된정보로_회원가입시_오류처리() throws Exception{
        //Given
        MemberReq memberReq1 = new MemberReq().builder()
                .userEmail("oh")
                .password("1234")
                .build();

        MemberReq memberReq2 = new MemberReq().builder()
                .userEmail("oh")
                .password("1234")
                .build();
        memberService.join(memberReq1);
        assertThrows(AlreadyJoinException.class, () -> memberService.join(memberReq2));
    }


    @Test
    void joinApi(){
        //http localhost:8080/hello?name=Spring
        TestRestTemplate rest = new TestRestTemplate();

        // Given
        String url = "http://localhost:8040/join";
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("testUser")
                .password("testPa1234")
                .build();
        ResponseEntity<String> res =
                rest.postForEntity(url, memberReq, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 비밀번호 8자 미만 일경우 API 테스트 실패
     */
    @Test
    void joinApiFail(){
        //http localhost:8080/hello?name=Spring
        TestRestTemplate rest = new TestRestTemplate();

        // Given
        String url = "http://localhost:8040/join";
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("testUser")
                .password("testPa")
                .build();
        ResponseEntity<String> res =
                rest.postForEntity(url, memberReq, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
