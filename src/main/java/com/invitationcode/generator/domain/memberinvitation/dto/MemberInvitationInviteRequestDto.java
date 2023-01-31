package com.invitationcode.generator.domain.memberinvitation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class MemberInvitationInviteRequestDto {

    @Positive
    @NotNull
    private Long memberIdx;

    @NotNull
    private String receiverEmail;
}
