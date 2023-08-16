package com.ohj.wanted_internship_bakend.app.restapi.member.controller;


import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "MemberControllerApi", description = "로그인 기능과 로그인 된 회원의 정보를 제공 기능을 담당하는 컨트롤러")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 API
     * [POST] /join
     *
     */
    @ResponseBody
    @PostMapping("/join")
    @Operation(summary =  "회원가입")
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
    @Operation(summary =  "로그인")
    public BaseResponse<Member> logIn(@Valid @RequestBody MemberReq memberReq) {
        try {
            return new BaseResponse<>(memberService.logIn(memberReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
