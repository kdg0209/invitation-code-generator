package com.invitationcode.generator.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "사용자 명")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment(value = "비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberInvitation> memberInvitations = new ArrayList<>();

    @Builder
    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getIdx() {
        return idx;
    }
}
