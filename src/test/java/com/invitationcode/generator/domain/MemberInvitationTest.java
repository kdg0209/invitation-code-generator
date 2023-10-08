package com.invitationcode.generator.domain;

import com.invitationcode.generator.domain.member.domain.Email;
import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.member.domain.Password;
import com.invitationcode.generator.domain.memberinvitation.domain.InviteCode;
import com.invitationcode.generator.domain.memberinvitation.domain.InviteStatus;
import com.invitationcode.generator.domain.memberinvitation.domain.MemberInvitation;
import com.invitationcode.generator.domain.memberinvitation.domain.ReceiverEmail;
import com.invitationcode.generator.global.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class MemberInvitationTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private Member member;

    @BeforeEach
    void setUp() {
        String id = "test";
        Password password = new Password("12345", PASSWORD_ENCODER);
        String name = "홍길동";
        Email email = new Email("test@naver.com");
        String nickName = "홍길동";

        member = Member.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .nickName(nickName)
                .build();
    }

    @Test
    void 정상적안_값을_사용하여_멤버_초대_객체를_생성할_수_있다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 멤버_초대_객체의_초대_상태를_완료_상태로_수정할_수_있다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        result.inviteCompleted();

        // then
        assertThat(result.getInviteStatus()).isEqualTo(InviteStatus.COMPLETED);
    }

    @Test
    void 멤버_초대_객체의_초대_상태를_거부_상태로_수정할_수_있다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        result.inviteRejected();

        // then
        assertThat(result.getInviteStatus()).isEqualTo(InviteStatus.REJECTED);
    }

    @Test
    void 멤버_초대_객체의_상태가_이미_완료_상태라면_예외를_발생시킨다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        result.inviteCompleted();
        assertThatThrownBy(result::verifyAlreadyCompletedOrAlreadyRejected)
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 멤버_초대_객체의_상태가_이미_거부_상태라면_예외를_발생시킨다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        result.inviteRejected();
        assertThatThrownBy(result::verifyAlreadyCompletedOrAlreadyRejected)
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 멤버_초대_객체의_취소여부의_상태를_변경시킬_수_있다() {

        // given
        ReceiverEmail receiverEmail = new ReceiverEmail("test@naver.com");
        InviteCode inviteCode = new InviteCode();
        Member newMember = this.member;

        // when
        MemberInvitation result = MemberInvitation.builder()
                .receiverEmail(receiverEmail)
                .inviteCode(inviteCode)
                .member(newMember)
                .build();

        // then
        result.cancelByMember();
        assertThat(result.getIsCancel()).isTrue();
    }
}