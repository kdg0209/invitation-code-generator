package com.invitationcode.generator.domain.member.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberHasCouponTest {

    @Test
    void 쿠폰의_만료일자가_남은_경우() {
        LocalDateTime now = LocalDateTime.of(2023, 02, 05, 11, 11, 11);
        LocalDateTime couponExpirationDateTime = LocalDateTime.of(2023, 02, 10, 11, 11, 11);

        assertThat(now.isAfter(couponExpirationDateTime)).isFalse();
    }

    @Test
    void 쿠폰의_만료일자가_지난_경우() {
        LocalDateTime now = LocalDateTime.of(2023, 02, 15, 11, 11, 11);
        LocalDateTime couponExpirationDateTime = LocalDateTime.of(2023, 02, 10, 11, 11, 11);

        assertThat(now.isAfter(couponExpirationDateTime)).isTrue();
    }
}