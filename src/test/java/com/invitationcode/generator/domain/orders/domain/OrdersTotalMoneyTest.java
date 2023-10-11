package com.invitationcode.generator.domain.orders.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrdersTotalMoneyTest {

    @Test
    void 유효한_BigDecimal_값을_사용하여_정상적인_객체를_생성할_수_있다() {

        // given
        BigDecimal bigDecimal = new BigDecimal("10000");

        // when
        OrdersTotalMoney result = new OrdersTotalMoney(bigDecimal);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo("10000");
    }

    @Test
    void 음수인_BigDecimal_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        BigDecimal bigDecimal = new BigDecimal("-10000");

        // when && then
        assertThatThrownBy(() -> new OrdersTotalMoney(bigDecimal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void NULL인_BigDecimal_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        BigDecimal bigDecimal = null;

        // when && then
        assertThatThrownBy(() -> new OrdersTotalMoney(bigDecimal))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 유효한_long_값을_사용하여_정상적인_객체를_생성할_수_있다() {

        // given
        long price = 10000L;

        // when
        OrdersTotalMoney result = new OrdersTotalMoney(price);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo("10000");
    }

    @Test
    void 음수인_long_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        long price = -10000L;

        // when && then
        assertThatThrownBy(() -> new OrdersTotalMoney(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void long의_범위가_넘어가는_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        long price = Long.MAX_VALUE + 1;

        // when && then
        assertThatThrownBy(() -> new OrdersTotalMoney(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plus_메서드를_사용하여_기존_금액에_금액을_추가할_수_있다() {

        // given
        BigDecimal price = new BigDecimal("10000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal plusPrice = new BigDecimal("30000");

        // when
        OrdersTotalMoney result = ordersTotalMoney.plus(plusPrice);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo("40000");
    }

    @Test
    void minus_메서드를_사용하여_기존_금액에_금액을_차감할_수_있다() {

        // given
        BigDecimal price = new BigDecimal("30000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal plusPrice = new BigDecimal("10000");

        // when
        OrdersTotalMoney result = ordersTotalMoney.minus(plusPrice);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo("20000");
    }

    @Test
    void isThanEqual_메서드를_사용하여_특정_금액과_같다면_TRUE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("30000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("30000");

        // when
        boolean result = ordersTotalMoney.isThanEqual(otherMoney);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void isThanEqual_메서드를_사용하여_특정_금액과_다르다면_FALSE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("30000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("50000");

        // when
        boolean result = ordersTotalMoney.isThanEqual(otherMoney);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void isLessThan_메서드를_사용하여_특정_금액_보다_작으면_TRUE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("30000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("50000");

        // when
        boolean result = ordersTotalMoney.isLessThan(otherMoney);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void isLessThan_메서드를_사용하여_특정_금액_보다_크다면_FALSE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("80000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("50000");

        // when
        boolean result = ordersTotalMoney.isLessThan(otherMoney);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void isThanGreater_메서드를_사용하여_특정_금액_보다_크다면_TRUE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("80000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("50000");

        // when
        boolean result = ordersTotalMoney.isThanGreater(otherMoney);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void isThanGreater_메서드를_사용하여_특정_금액_보다_작다면_FALSE_를_반환한다() {

        // given
        BigDecimal price = new BigDecimal("20000");
        OrdersTotalMoney ordersTotalMoney = new OrdersTotalMoney(price);
        BigDecimal otherMoney = new BigDecimal("50000");

        // when
        boolean result = ordersTotalMoney.isThanGreater(otherMoney);

        // then
        assertThat(result).isFalse();
    }
}