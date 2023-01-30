package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EmailTest {

    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    @Test
    @DisplayName("비정상적인 이메일 생성 1")
    void 비정상적인_이메일_생성_1() {

        // given
        String inputEmail = "test@naver";

        // then
        assertThat(inputEmail.matches(REGEX)).isFalse();
    }

    @Test
    @DisplayName("비정상적인 이메일 생성 2")
    void 비정상적인_이메일_생성_2() {

        // given
        String inputEmail = "testnaver.com";

        // then
        assertThat(inputEmail.matches(REGEX)).isFalse();
    }

    @Test
    @DisplayName("정상적인 이메일 생성")
    void 정상적인_이메일_생성() {

        // given
        String inputEmail = "test@naver.co.kr";

        // then
        assertThat(inputEmail.matches(REGEX)).isTrue();
    }

}