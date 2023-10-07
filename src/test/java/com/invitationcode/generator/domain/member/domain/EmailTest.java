package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EmailTest {

    @Test
    void 도메인의_주소가_올바르지_않은_경우_예외를_발생시긴다() {

        // given
        String input = "test@naver";

        // when && then
        assertThatThrownBy(() -> new Email(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 도메인의_주소에서_골뱅이가_없는_경우_올바르지_않은_경우_예외를_발생시긴다() {

        // given
        String input = "testnaver.com";

        assertThatThrownBy(() -> new Email(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정상적인 이메일 생성")
    void 정상적인_이메일_생성() {

        // given
        String input = "test@naver.co.kr";

        // when
        Email email = new Email(input);

        // then
        assertThat(email.getEmail()).isEqualTo("test@naver.co.kr");
    }

}