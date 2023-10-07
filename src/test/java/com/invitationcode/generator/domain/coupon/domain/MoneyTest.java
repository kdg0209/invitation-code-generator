package com.invitationcode.generator.domain.coupon.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void 정상적인_Integer_의_범위를_사용하여_Money_객체를_만들_수_있다() {

        // given
        Integer integer = 1000;

        // when
        Money result = new Money(integer);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 비정상적인_Integer_의_범위를_사용하여_Money_객체를_만들_수_있다() {

        // given
        Integer integer = Integer.MAX_VALUE + 1;

        // when && then
        assertThatThrownBy(() -> new Money(integer))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void NULL인_Integer를_사용하여_Money_객체를_만들_수_있다() {

        // given
        Integer integer = null;

        // when && then
        assertThatThrownBy(() -> new Money(integer))
                .isInstanceOf(IllegalArgumentException.class);
    }
}