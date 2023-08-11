package com.ohj.wanted_internship_bakend.app.restapi.post.controller;

import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import com.ohj.wanted_internship_bakend.app.restapi.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "PostController", description = "게시글 관련 정보")
public class PostController {
    private final PostService postService;

    /**
     * 게시글 작성
     * @param postReq
     * @return
     */
    @PostMapping("/write")
    @Operation(summary =  "게시글 생성 ", security = @SecurityRequirement(name = "X-ACCESS-TOKEN"))
    public BaseResponse<Post> postWrite(@Valid @RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.write(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 수정
     * @param postReq
     * @return
     */
    @PutMapping("/write")
    @Operation(summary =  "게시글 수정", security = @SecurityRequirement(name = "X-ACCESS-TOKEN"))
    public BaseResponse<Post> putWrite(@Valid @RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.put(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 삭제
     * @param postReq
     * @return
     */
    @DeleteMapping("/write")
    @Operation(summary =  "게시글 삭제 ", security = @SecurityRequirement(name = "X-ACCESS-TOKEN"))
    public BaseResponse<Post> deleteWrite(@RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.delete(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 상세 조회
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    @Operation(summary =  "게시글 상세조회 ")
    public BaseResponse<Post> getPostDetail(@PathVariable Long postId) {
        try{
            return new BaseResponse<>(postService.getPostDetail(postId));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 목록 조회 (페이징)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지에 표시할 게시글 수
     * @return
     */
    @GetMapping("/list")
    @Operation(summary =  "게시글 페이징 조회")
    public BaseResponse<Page<Post>> getPostList(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Post> postPage = postService.getPostList(pageable);
            return new BaseResponse<>(postPage);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
