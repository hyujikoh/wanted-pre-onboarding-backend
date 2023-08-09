package com.ohj.wanted_internship_bakend.member;

import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-09
 * Desc : 엔드포인트 테스트 클래스
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@ActiveProfiles("test")
public class MemberApiTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입_성공_api(){
        TestRestTemplate rest = new TestRestTemplate();

        // Given
        String url = "http://localhost:8040/join";
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("testUser@gmail.com")
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
    void 회원가입_실패_api(){
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

    @Test
    void 로그인_성공_api(){
        TestRestTemplate rest = new TestRestTemplate();

        MemberReq joinReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();
        memberService.join(joinReq);
        // Given
        String url = "http://localhost:8040/logIn";
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();

        ResponseEntity<String> res =
                rest.postForEntity(url, memberReq, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void 로그인_실패_유효성_api(){
        TestRestTemplate rest = new TestRestTemplate();

        // Given
        String url = "http://localhost:8040/logIn";
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("1234")
                .build();
        ResponseEntity<String> res =
                rest.postForEntity(url, memberReq, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
