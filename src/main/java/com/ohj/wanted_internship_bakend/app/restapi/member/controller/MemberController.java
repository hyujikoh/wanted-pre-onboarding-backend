package com.ohj.wanted_internship_bakend.app.restapi.member.controller;


import com.ohj.wanted_internship_bakend.app.common.BaseException;
import com.ohj.wanted_internship_bakend.app.common.BaseResponse;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.Member;
import com.ohj.wanted_internship_bakend.app.restapi.member.domain.MemberReq;
import com.ohj.wanted_internship_bakend.app.restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Author : hyujikoh
 * CreatedAt : 2023-08-08
 */
@RestController
@RequiredArgsConstructor
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

//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     *
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
//        try {
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
