package com.invitationcode.generator.domain.memberinvitation.domain;

import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
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

    @Embedded
    private ReceiverEmail receiverEmail;

    @Comment(value = "초대한 사용자에 의한 취소 여부")
    @Column(name = "is_cancel", nullable = false)
    private Boolean isCancel;

    @Comment(value = "응답 상태")
    @Column(name = "invite_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private InviteStatus inviteStatus;

    @Embedded
    private InviteCode inviteCode;

    @Comment(value = "초대한 유저의 아이디")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "member_invitation_member"))
    private Member member;

    @Comment(value = "초대 코드 생성 시간")
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Comment(value = "초대 거절 시간")
    @Column(name = "rejected_datetime")
    private LocalDateTime rejectedDatetime;

    @Comment(value = "초대 완료 시간")
    @Column(name = "completed_datetime")
    private LocalDateTime completedDatetime;

    @Builder
    public MemberInvitation(ReceiverEmail receiverEmail, InviteCode inviteCode, Member member) {
        this.receiverEmail = receiverEmail;
        this.isCancel = false;
        this.inviteStatus = InviteStatus.WAITING;
        this.inviteCode = inviteCode;
        this.member = member;
        this.createdDatetime = LocalDateTime.now();
    }

    public Long getIdx() {
        return idx;
    }

    public void verifyAlreadyCompletedOrAlreadyRejected() {
        if (inviteStatus == InviteStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.MEMBER_INVITATION_ALREADY_COMPLETED);
        }
        if (inviteStatus == InviteStatus.REJECTED) {
            throw new BusinessException(ErrorCode.MEMBER_INVITATION_ALREADY_REJECTED);
        }
    }

    public void verifyInviteCodeExpired() {
        LocalDateTime expiredDay = this.createdDatetime.plusHours(24);

        if (LocalDateTime.now().isAfter(expiredDay)) {
            throw new BusinessException(ErrorCode.MEMBER_INVITATION_INVITE_CODE_EXPIRED);
        }
    }

    public void inviteCompleted() {
        this.inviteStatus = InviteStatus.COMPLETED;
        this.completedDatetime = LocalDateTime.now();
    }

    public void inviteRejected() {
        this.inviteStatus = InviteStatus.REJECTED;
        this.rejectedDatetime = LocalDateTime.now();
    }

    public void cancelByMember() {
        this.isCancel = true;
    }
}
