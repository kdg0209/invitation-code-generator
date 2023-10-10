package com.invitationcode.generator.domain.memberinvitation.service;

import com.invitationcode.generator.domain.member.dao.MemberDao;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.memberinvitation.dao.MemberInvitationDao;
import com.invitationcode.generator.domain.memberinvitation.domain.InviteCode;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.domain.memberinvitation.domain.ReceiverEmail;
import com.invitationcode.generator.domain.memberinvitation.repository.MemberInvitationRepository;
import com.invitationcode.generator.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

//@SpringBootTest
class MemberInvitationServiceTest {


//    @Autowired
//    private MemberDao memberDao;
//
//    @Autowired
//    private MemberInvitationDao memberInvitationDao;
//
//    @Autowired
//    private MemberInvitationRepository memberInvitationRepository;
//
//    @Test
//    void 유효하지않은_사용자에의한_초대() {
//
//        // given
//        Long memberIdx = 1L;
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> {
//            Member member = memberDao.findByIdx(memberIdx);
//        }).isInstanceOf(BusinessException.class);
//    }
//
//    @Test
//    void 유효한_사용자에의한_초대이지만_이미_초대한_사용자인_경우() {
//
//        // given
//        Long memberIdx = 12L;
//        Member member = memberDao.findByIdx(memberIdx);
//
//        // when
//        String receiverEmail = "test@naver.com";
//
//        // then
//        assertThatThrownBy(() -> {
//            memberInvitationDao.verifyWaitingMemberInvitation(receiverEmail);
//        }).isInstanceOf(BusinessException.class);
//    }
//
//    @Test
//    void 유효한_사용자에의한_초대() {
//
//        // given
//        Long memberIdx = 12L;
//        Member member = memberDao.findByIdx(memberIdx);
//
//        // when
//        String receiverEmail = "test1@naver.com";
//        memberDao.verifyDuplicatedMemberEmail(receiverEmail);
//
//        MemberInvitation memberInvitation = MemberInvitation.builder()
//                .receiverEmail(new ReceiverEmail(receiverEmail))
//                .inviteCode(new InviteCode())
//                .member(member)
//                .build();
//
//        MemberInvitation save = memberInvitationRepository.save(memberInvitation);
//
//        // then
//        assertThat(save.getIdx()).isEqualTo(2L);
//    }
}