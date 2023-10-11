package com.invitationcode.generator.domain.product.domain;

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
    void 비정상적인_Integer_의_범위를_사용하여_Money_객체를_만들경우_예외가_발생한다() {

        // given
        Integer price = Integer.MAX_VALUE + 1;

        // when && then
        assertThatThrownBy(() -> new Money(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음수인_Integer를_사용하여_Money_객체를_만들경우_예외가_발생한다() {

        // given
        Integer price = -1000;

        // when && then
        assertThatThrownBy(() -> new Money(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}