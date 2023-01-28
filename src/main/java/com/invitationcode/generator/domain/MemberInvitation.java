package com.invitationcode.generator.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity(name = "member_invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "받는 사람 이메일 주")
    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Comment(value = "취소 여부")
    @Column(name = "is_cancel", nullable = false)
    private Boolean isCancel;

    @Embedded
    private InviteCode inviteCode;

    @Comment(value = "초대한 유저의 아이디")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "member_invitation_member"))
    private Member member;

    @Comment(value = "초대 코드 생성 시간")
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Builder
    public MemberInvitation(String receiverEmail, InviteCode inviteCode, Member member) {
        this.receiverEmail = receiverEmail;
        this.isCancel = false;
        this.inviteCode = inviteCode;
        this.member = member;
        this.createdDatetime = LocalDateTime.now();
    }

    public Long getIdx() {
        return idx;
    }
}
