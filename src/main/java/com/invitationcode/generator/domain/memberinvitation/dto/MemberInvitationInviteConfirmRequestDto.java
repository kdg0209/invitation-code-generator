package com.invitationcode.generator.domain.memberinvitation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberInvitationInviteConfirmRequestDto {

    @NotNull
    private String receiverEmail;
}
