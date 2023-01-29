package com.invitationcode.generator.domain.member.controller;

import com.invitationcode.generator.domain.member.dto.MemberCreateRequestDto;
import com.invitationcode.generator.domain.member.service.MemberService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponse<Long> create(@Valid @RequestBody MemberCreateRequestDto requestDto) {
        Long response = memberService.create(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }
}
