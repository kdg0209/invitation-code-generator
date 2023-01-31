package com.invitationcode.generator.domain.memberinvitation.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.memberinvitation.dao.MemberInvitationDao;
import com.invitationcode.generator.domain.memberinvitation.domain.InviteCode;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.domain.memberinvitation.domain.ReceiverEmail;
import com.invitationcode.generator.domain.memberinvitation.dto.MemberInvitationInviteRequestDto;
import com.invitationcode.generator.domain.memberinvitation.repository.MemberInvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberInvitationService {

    private final MemberDao memberDao;
    private final MemberInvitationDao memberInvitationDao;
    private final MemberInvitationRepository memberInvitationRepository;

    public Long invite(MemberInvitationInviteRequestDto requestDto) {
        Member member = memberDao.findByIdx(requestDto.getMemberIdx());
        memberDao.verifyDuplicatedMemberEmail(requestDto.getReceiverEmail());
        memberInvitationDao.verifyWaitingMemberInvitation(requestDto.getReceiverEmail());

        InviteCode inviteCode = new InviteCode();
        ReceiverEmail receiverEmail = new ReceiverEmail(requestDto.getReceiverEmail());
        MemberInvitation memberInvitation = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(member)
                .build();

        memberInvitationRepository.save(memberInvitation);
        return memberInvitation.getIdx();
    }

    // 초대 후 회원가입 루트
    public Long inviteConfirm(String inviteCode) {
        MemberInvitation memberInvitation = memberInvitationDao.findByInviteCode(inviteCode);
        memberInvitation.verifyAlreadyCompletedOrAlreadyRejected();
        memberInvitation.verifyInviteCodeExpired();
        memberInvitation.inviteCompleted();
        return memberInvitation.getIdx();
    }

    public void inviteReject(String inviteCode) {
        MemberInvitation memberInvitation = memberInvitationDao.findByInviteCode(inviteCode);
        memberInvitation.verifyAlreadyCompletedOrAlreadyRejected();
        memberInvitation.verifyInviteCodeExpired();
        memberInvitation.inviteRejected();
    }

    public void inviteCancel(Long memberIdx, String inviteCode) {
        memberDao.findByIdx(memberIdx);
        MemberInvitation memberInvitation = memberInvitationDao.findByInviteCode(inviteCode);
        memberInvitation.verifyAlreadyCompletedOrAlreadyRejected();
        memberInvitation.verifyInviteCodeExpired();
        memberInvitation.cancelByMember();
    }
}
