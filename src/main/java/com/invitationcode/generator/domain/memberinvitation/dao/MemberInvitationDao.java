package com.invitationcode.generator.domain.memberinvitation.dao;

import com.invitationcode.generator.domain.memberinvitation.domain.InviteStatus;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.invitationcode.generator.domain.memberinvitation.domain.QMemberInvitation.memberInvitation;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberInvitationDao {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberInvitation findByInviteCode(String inviteCode) {
        MemberInvitation result = jpaQueryFactory
                .selectFrom(memberInvitation)
                .where(
                        memberInvitation.inviteCode.inviteCode.eq(inviteCode)
                )
                .fetchOne();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_INVITATION_NOT_FOUND_EXCEPTION));
    }

    public void verifyWaitingMemberInvitation(String receiverEmail) {
        Integer result = jpaQueryFactory
                .selectOne()
                .from(memberInvitation)
                .where(
                        memberInvitation.receiverEmail.receiverEmail.eq(receiverEmail),
                        memberInvitation.inviteStatus.eq(InviteStatus.WAITING)
                )
                .fetchOne();

        if (result != null) {
            throw new BusinessException(ErrorCode.MEMBER_INVITATION_ALREADY_WAITING);
        }
    }
}
