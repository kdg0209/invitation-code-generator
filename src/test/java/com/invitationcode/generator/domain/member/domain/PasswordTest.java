package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PasswordTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Test
    void 정상적인_비밀번호_객체를_생성할_수_있다() {

        // given
        String password = "12345";

        // when
        Password result = new Password(password, PASSWORD_ENCODER);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void NULL을_사용하여_비밀번호_객체를_생성하는_경우_예외를_발생시킨다() {

        // given
        String password = null;

        // when && then
        assertThatThrownBy(() -> new Password(password, PASSWORD_ENCODER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 빈문자열을_사용하여_비밀번호_객체를_생성하는_경우_예외를_발생시킨다() {

        // given
        String password = "";

        // when && then
        assertThatThrownBy(() -> new Password(password, PASSWORD_ENCODER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공백이_포함된_문자열을_사용하여_비밀번호_객체를_생성하는_경우_예외를_발생시킨다() {

        // given
        String password = " ";

        // when && then
        assertThatThrownBy(() -> new Password(password, PASSWORD_ENCODER))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 새로운_문자열을_통해_비밀번호를_변경할_수_있다() {

        // given
        String oldPwd = "12345";
        Password password = new Password(oldPwd, PASSWORD_ENCODER);

        // when
        String newPwd = "1234567890";
        password.changePassword(oldPwd, newPwd, PASSWORD_ENCODER);

        // then
        assertThat(PASSWORD_ENCODER.matches(newPwd, password.getPassword())).isTrue();
    }

    @Test
    void 기존_비밀번호가_일치하지_않는_문자열로_비밀번호를_수정하는_경우_예외가_발생한다() {

        // given
        String oldPwd = "12345";
        Password password = new Password(oldPwd, PASSWORD_ENCODER);

        // when && then
        String wrongOldPwd = "0000";
        String newPwd = "1234567890";
        assertThatThrownBy(() -> password.changePassword(wrongOldPwd, newPwd, PASSWORD_ENCODER))
                .isInstanceOf(IllegalArgumentException.class);
    }
}