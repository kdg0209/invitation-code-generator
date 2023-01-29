package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void 비밀번호_생성() {

        // given
        Password password = new Password("12345");

        // when
        String encodedPassword = passwordEncoder.encode(password.getPassword());

        // then
        assertThat(passwordEncoder.matches(password.getPassword(), encodedPassword)).isTrue();
    }

}