package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberTest {

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
    void 정상적인_맴버_객체를_생성할_수_있다() {

        // given && when
        Member result = member;

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test");
    }

    @Test
    void 멤버_객체의_닉네임을_수정할_수_있다() {

        // given
        String nickName = "홍길동 123";

        // when
        member.updateNickName(nickName);

        // then
        assertThat(member.getNickName()).isEqualTo("홍길동 123");
    }

    @Test
    void 멤버_객체의_비밀번호를_수정할_수_있다() {

        // given
        String oldPwd = "12345";
        String newPwd = "1234567890";
        PasswordEncoder encoder = PASSWORD_ENCODER;

        // when
        member.changeablePassword(oldPwd, newPwd, encoder);

        // then
        assertThat(PASSWORD_ENCODER.matches(newPwd, member.passwordToString())).isTrue();
    }

    @Test
    void 멤버_객체의_탈퇴여부를_수정할_수_있다() {

        // given && when
        member.delete();

        // then
        assertThat(member.getIsDeleted()).isTrue();
    }
}