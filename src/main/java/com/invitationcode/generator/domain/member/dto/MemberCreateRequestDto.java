package com.invitationcode.generator.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberCreateRequestDto {

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;
}
