package com.invitationcode.generator.domain.coupon.domain;

import com.invitationcode.generator.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CouponTest {

    @Test
    void 정상적인_값을_사용히여_쿠폰_객체를_생성한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);

        // when
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 재고가_음수인_값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = -1000;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액이_음수인_값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        String name = "쿠폰 1";
        int stock = 10;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(new Money(-1000))
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_공백인_값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "     ";
        int stock = 10;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_NULL인_값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = null;
        int stock = 10;
        LocalDateTime expirationDateTime = LocalDateTime.of(9999, 12, 31, 11, 59, 0);

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 만료일자가_NULL인_값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 10;
        LocalDateTime expirationDateTime = null;

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(BusinessException.class);
    }

    @Test
    void 만료일자가_현재시간보다_이전값을_사용하여_쿠폰_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 10;
        LocalDateTime expirationDateTime = LocalDateTime.of(2022, 12, 31, 11, 59, 0);

        // when && then
        assertThatThrownBy(() -> Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build()
        ).isInstanceOf(BusinessException.class);
    }

    @Test
    void 만료일자가_현재시간_이후_값을_사용하여_쿠폰_객체를_생성하는_경우_정상적으로_생성할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 10;
        LocalDateTime expirationDateTime = LocalDateTime.of(9999, 12, 31, 11, 59, 0);

        // when
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 쿠폰의_이름을_정상적인_값을_사용하여_업데이트할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when
        String updateName = "coupon name 22";
        result.updateName(updateName);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("coupon name 22");
    }

    @Test
    void 쿠폰의_이름을_NULL_값을_사용하여_업데이트하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        String updateName = null;
        assertThatThrownBy(() -> result.updateName(updateName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰의_이름을_빈_공백_값을_사용하여_업데이트하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        String updateName = "       ";
        assertThatThrownBy(() -> result.updateName(updateName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰의_재고를_정상적인_값을_사용하여_업데이트할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();


        // when
        int updateStock = 200;
        result.updateStock(updateStock);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStock()).isEqualTo(200);
    }

    @Test
    void 쿠폰의_재고를_음수_값을_사용하여_업데이트하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        int updateStock = -200;
        assertThatThrownBy(() -> result.updateStock(updateStock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰의_금액을_정상적인_값을_사용하여_업데이트할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();


        // when
        result.updateMoney(new Money(100000));

        // then
        assertThat(result.getMoney()).isEqualTo("100000");
    }

    @Test
    void 쿠폰의_만료일자를_현재시간보다_이후_값을_사용하여_업데이트할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when
        LocalDateTime updateExpirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        result.updateExpirationDateTime(updateExpirationDateTime);

        // then
        assertThat(result.getExpirationDateTime()).isEqualTo(updateExpirationDateTime);
    }

    @Test
    void 쿠폰의_만료일자를_현재시간보다_이전_값을_사용하여_업데이트하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        LocalDateTime updateExpirationDateTime = LocalDateTime.of(2012, 12, 31, 11, 59, 0);
        assertThatThrownBy(() -> result.updateExpirationDateTime(updateExpirationDateTime))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 쿠폰의_만료일자를_NULL_값을_사용하여_업데이트하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        LocalDateTime updateExpirationDateTime = null;
        assertThatThrownBy(() -> result.updateExpirationDateTime(updateExpirationDateTime))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 현재_남은_재고보다_적은_정수의_값을_사용히여_쿠폰의_재고를_감소할_수_있다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when
        int decreaseStock = 10;
        result.decreaseStock(decreaseStock);

        // then
        assertThat(result.getStock()).isEqualTo(90);
    }

    @Test
    void 현재_남은_재고보다_더_많은_정수의_값을_사용히여_쿠폰의_재고를_감소하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        int decreaseStock = 2000;
        assertThatThrownBy(() -> result.decreaseStock(decreaseStock))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 음수의_값을_사용히여_쿠폰의_재고를_감소하는_경우_예외가_발생한다() {

        // given
        Money money = new Money(1000);
        String name = "쿠폰 1";
        int stock = 100;
        LocalDateTime expirationDateTime = LocalDateTime.of(2025, 12, 31, 11, 59, 0);
        Coupon result = Coupon.builder()
                .money(money)
                .name(name)
                .stock(stock)
                .expirationDateTime(expirationDateTime)
                .build();

        // when && then
        int decreaseStock = -100;
        assertThatThrownBy(() -> result.decreaseStock(decreaseStock))
                .isInstanceOf(BusinessException.class);
    }
}