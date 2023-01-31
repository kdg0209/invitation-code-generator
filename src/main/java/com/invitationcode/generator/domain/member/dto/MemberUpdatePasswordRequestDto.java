package com.invitationcode.generator.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberUpdatePasswordRequestDto {

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
