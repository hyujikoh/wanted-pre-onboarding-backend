package com.ohj.wanted_internship_bakend.post;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-10
 * Desc : 서비스 로직 테스트
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @LocalServerPort
    private int port;

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
    public void 게시글_등록_유효하지않은_JWT() throws Exception{
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

        String wrongJwt = "wrongJwt";

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", wrongJwt)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReq)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists()) // 예상되는 에러 메시지가 있는지 확인
                .andExpect(jsonPath("$.message").value("유효하지 않은 JWT 입니다.")); // 예상되는 에러 메시지와 일치하는지 확인
        long count = postService.getCount();

        assertThat(count).isEqualTo(0);
        // Then
    }

    @Test
    public void 게시글_수정() throws Exception {
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


        // 게시글 등록
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReq)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(contentAsString);

        int id = rootNode.path("result").path("id").asInt();

        // 게시글 수정

        PostReq postReqNew = new PostReq().builder()
                .id(id)
                .subject("안녕하세요")
                .content("수정된 상세내용입니다")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReqNew)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        long count = postService.getCount();

        assertThat(count).isEqualTo(1);

    }

    @Test
    public void 게시글_수정_유효하지않은_JWT() throws Exception {
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


        // 게시글 등록
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReq)))
                .andExpect(status().isOk())
                .andReturn();

        // 게시글 수정

        MemberReq memberReq2 = new MemberReq().builder()
                .userEmail("Th@naver.com")
                .password("12345678")
                .build();
        Member member2 = memberService.join(memberReq2);

        String accessTokenMember2 = member2.getAccessToken();

        PostReq postReqNew = new PostReq().builder()
                .id(1)
                .subject("안녕하세요")
                .content("상세내용입니다123123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessTokenMember2)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReqNew)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists()) // 예상되는 에러 메시지가 있는지 확인
                .andExpect(jsonPath("$.message").value("유효하지 않은 사용자 입니다.")); // 예상되는 에러 메시지와 일치하는지 확인

        long count = postService.getCount();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 게시글_삭제() throws Exception {

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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReq)))
                .andExpect(status().isOk())
                .andReturn();


        // 게시글 삭제
        String contentAsString = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(contentAsString);

        int id = rootNode.path("result").path("id").asInt();
        // Then


        long count = postService.getCount();

        assertThat(count).isEqualTo(1);



        PostReq postReqWillDel = new PostReq().builder()
                .id(id)
                .build();

        // When
        MvcResult mvcResultDelete = mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:"+port+"/post/write")
                        .header("X-ACCESS-TOKEN", accessToken)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postReqWillDel)))
                .andExpect(status().isOk())
                .andReturn();




        // Then



        assertThat(postService.getCount()).isEqualTo(0);

    }

    @Test
    public void 게시글_삭제_권한오류() {

    }
}
