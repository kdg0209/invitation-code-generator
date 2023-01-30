package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


    @Test
    void 비밀번호_생성() {

        // given
        Password password = new Password("12345", PASSWORD_ENCODER);

        // when
        String encodedPassword = PASSWORD_ENCODER.encode(password.getPassword());

        // then
        assertThat(PASSWORD_ENCODER.matches(password.getPassword(), encodedPassword)).isTrue();
    }

}