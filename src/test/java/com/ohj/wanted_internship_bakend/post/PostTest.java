package com.ohj.wanted_internship_bakend.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import com.ohj.wanted_internship_bakend.app.restapi.post.repository.PostRepository;
import com.ohj.wanted_internship_bakend.app.restapi.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-10
 * Desc : 서비스 로직 테스트
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PostTest {
    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();
        memberService.join(memberReq);
    }

    @Test
    public void 게시글_등록() throws Exception {
        // Given
        MemberReq memberReq = new MemberReq().builder()
                .userEmail("oh@naver.com")
                .password("12345678")
                .build();

        Member member = memberService.logIn(memberReq);

        PostReq postReq = new PostReq().builder()
                .subject("안녕하세요")
                .content("상세내용입니다")
                .build();

        String accessToken = member.getAccessToken();

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8010/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReq)))
                .andExpect(status().isOk())
                .andReturn();

        // Then

        long count = postService.getCount();

        assertThat(count).isEqualTo(1);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void 게시글_등록_유효하지않은_JWT() {

    }

    @Test
    public void 게시글_수정() {

    }

    @Test
    public void 게시글_수정_유효하지않은_JWT() {

    }

    @Test
    public void 게시글_삭제() {

    }

    @Test
    public void 게시글_삭제_권한오류() {

    }
}
