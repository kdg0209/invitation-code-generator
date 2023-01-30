package com.invitationcode.generator.domain.member.controller;

import com.invitationcode.generator.domain.member.dto.MemberCreateRequestDto;
import com.invitationcode.generator.domain.member.dto.MemberFindResponseDto;
import com.invitationcode.generator.domain.member.dto.MemberUpdateRequestDto;
import com.invitationcode.generator.domain.member.service.MemberFindService;
import com.invitationcode.generator.domain.member.service.MemberService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_200;
import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberFindService memberFindService;

    @GetMapping("/{memberIdx}")
    public BaseResponse<MemberFindResponseDto> find(@PathVariable(value = "memberIdx") Long memberIdx) {
        MemberFindResponseDto response = memberFindService.find(memberIdx);
        return new BaseResponse<>(CODE_200, response);
    }

    @PostMapping
    public BaseResponse<Long> create(@Valid @RequestBody MemberCreateRequestDto requestDto) {
        Long response = memberService.create(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }

    @PutMapping("/{memberIdx}")
    public BaseResponse<Long> update(@PathVariable(value = "memberIdx") Long memberIdx, @Valid @RequestBody MemberUpdateRequestDto requestDto) {
        Long response = memberService.update(memberIdx, requestDto);
        return new BaseResponse<>(CODE_200, response);
    }

    @DeleteMapping("/{memberIdx}")
    public BaseResponse<Void> delete(@PathVariable(value = "memberIdx") Long memberIdx) {
        memberService.delete(memberIdx);
        return new BaseResponse<>(CODE_200);
    }

}
