package com.invitationcode.generator.domain.member.dto;

import com.invitationcode.generator.domain.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberFindResponseDto {

    private Long idx;
    private String id;
    private String name;
    private String nickName;
    private String email;

    public MemberFindResponseDto(Member member) {
        this.idx = member.getIdx();
        this.id = member.getId();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
    }
}
