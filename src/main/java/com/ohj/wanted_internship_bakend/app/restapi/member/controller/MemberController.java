package com.ohj.wanted_internship_bakend.app.restapi.member.controller;


import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 API
     * [POST] /join
     *
     */
    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<Member> createUser(@Valid @RequestBody MemberReq memberReq) {
        try{
            return new BaseResponse<>(memberService.join(memberReq));
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     *
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<Member> logIn(@Valid @RequestBody MemberReq memberReq) {
        try {
            return new BaseResponse<>(memberService.logIn(memberReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
