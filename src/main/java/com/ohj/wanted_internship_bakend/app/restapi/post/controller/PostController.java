package com.ohj.wanted_internship_bakend.app.restapi.post.controller;

import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.Post;
import com.ohj.wanted_internship_bakend.app.restapi.post.domain.PostReq;
import com.ohj.wanted_internship_bakend.app.restapi.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    /**
     * 게시글 작성
     * @param postReq
     * @return
     */
    @PostMapping("/write")
    public BaseResponse<Post> postWrite(@Valid @RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.write(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 작성
     * @param postReq
     * @return
     */
    @PutMapping("/write")
    public BaseResponse<Post> putWrite(@Valid @RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.put(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 작성
     * @param postReq
     * @return
     */
    @DeleteMapping("/write")
    public BaseResponse<Post> deleteWrite(@Valid @RequestBody  PostReq postReq) {
        try{
            return new BaseResponse<>(postService.delete(postReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
