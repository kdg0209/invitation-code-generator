package com.invitationcode.generator.domain.coupon.domain;

import com.invitationcode.generator.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class CouponTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 정상적인_쿠폰_생성() {

        // given
        Coupon coupon = Coupon.builder()
                .name("할인 쿠폰")
                .stock(10)
                .expirationDateTime(LocalDateTime.now())
                .money(new Money(10000))
                .build();

        // when
        Coupon save = couponRepository.save(coupon);

        // then
        assertThat(save.getIdx()).isEqualTo(2L);
    }

    @Test
    void 재고가_마이너스인_쿠폰_생성() {

        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name("할인 쿠폰")
                    .stock(-1)
                    .expirationDateTime(LocalDateTime.now())
                    .money(new Money(10000))
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 재고가_NULL인_쿠폰_생성() {
        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name("할인 쿠폰")
                    .stock(null)
                    .expirationDateTime(LocalDateTime.now())
                    .money(new Money(10000))
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액이_마이너스인_쿠폰_생성() {
        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name("할인 쿠폰")
                    .stock(10)
                    .expirationDateTime(LocalDateTime.now())
                    .money(new Money(-15000000))
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액이_NULL인_쿠폰_생성() {
        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name("할인 쿠폰")
                    .stock(10)
                    .expirationDateTime(LocalDateTime.now())
                    .money(new Money(null))
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_NULL인_쿠폰_생성() {
        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name(null)
                    .stock(10)
                    .expirationDateTime(LocalDateTime.now())
                    .money(new Money(10000))
                    .build();
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void 만료일자가_NULL인_쿠폰_생성() {
        assertThatThrownBy(() -> {
            Coupon.builder()
                    .name("test")
                    .stock(10)
                    .expirationDateTime(null)
                    .money(new Money(10000))
                    .build();
        }).isInstanceOf(NullPointerException.class);
    }
}