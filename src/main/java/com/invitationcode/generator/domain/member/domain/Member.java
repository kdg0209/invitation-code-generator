package com.invitationcode.generator.domain.member.domain;


import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
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

    @Comment(value = "아이디")
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Embedded
    private Password password;

    @Comment(value = "사용자 명")
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Email email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberInvitation> memberInvitations = new ArrayList<>();

    @Builder
    public Member(String id, Password password, String name, Email email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Long getIdx() {
        return idx;
    }
}
