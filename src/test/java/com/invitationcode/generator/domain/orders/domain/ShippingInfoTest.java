package com.invitationcode.generator.domain.orders.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingInfoTest {

    @Test
    void 유효한_값을_사용하여_배송정보_객체를_생성할_수_있다() {

        // given
        Integer zipcode = 100;
        String address = "Seoul";
        String addressDetail = "강남구";
        String message = "배송전 연락바랍니다.";

        // when
        ShippingInfo result = ShippingInfo.builder()
                .zipcode(zipcode)
                .address(address)
                .addressDetail(addressDetail)
                .message(message)
                .build();

        // then
        assertThat(result).isNotNull();
    }
}