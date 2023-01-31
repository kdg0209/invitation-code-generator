package com.invitationcode.generator.domain.memberinvitation.controller;

import com.invitationcode.generator.domain.memberinvitation.dto.MemberInvitationInviteRequestDto;
import com.invitationcode.generator.domain.memberinvitation.service.MemberInvitationService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_200;
import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;

@RestController
@RequestMapping("/member-invitation")
@RequiredArgsConstructor
public class MemberInvitationController {

    private final MemberInvitationService memberInvitationService;

    @PostMapping
    public BaseResponse<Long> invite(@Valid @RequestBody MemberInvitationInviteRequestDto requestDto) {
        Long response = memberInvitationService.invite(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }

    @PutMapping("/confirm/{inviteCode}")
    public BaseResponse<Long> inviteConfirm(@PathVariable(name = "inviteCode") String inviteCode) {
        Long response = memberInvitationService.inviteConfirm(inviteCode);
        return new BaseResponse<>(CODE_201, response);
    }

    @PutMapping("/reject/{inviteCode}")
    public BaseResponse<Void> inviteReject(@PathVariable(name = "inviteCode") String inviteCode) {
        memberInvitationService.inviteReject(inviteCode);
        return new BaseResponse<>(CODE_200);
    }

    @DeleteMapping("/{memberIdx}/cancel/{inviteCode}")
    public BaseResponse<Void> inviteCancel(@PathVariable(name = "memberIdx") Long memberIdx, @PathVariable(name = "inviteCode") String inviteCode) {
        memberInvitationService.inviteCancel(memberIdx, inviteCode);
        return new BaseResponse<>(CODE_200);
    }

}
