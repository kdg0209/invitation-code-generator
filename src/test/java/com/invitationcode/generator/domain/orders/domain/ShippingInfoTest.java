package com.invitationcode.generator.domain.orders.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

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

    @Test
    void 우편번호가_음수인_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Integer zipcode = -100;
        String address = "Seoul";
        String addressDetail = "강남구";
        String message = "배송전 연락바랍니다.";

        // when && then
        assertThatThrownBy(() -> ShippingInfo.builder()
            .zipcode(zipcode)
            .address(address)
            .addressDetail(addressDetail)
            .message(message)
            .build()
        )
        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공백인_주소_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Integer zipcode = 100;
        String address = "          ";
        String addressDetail = "강남구";
        String message = "배송전 연락바랍니다.";

        // when && then
        assertThatThrownBy(() -> ShippingInfo.builder()
            .zipcode(zipcode)
            .address(address)
            .addressDetail(addressDetail)
            .message(message)
            .build()
        )
        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void NULL인_주소_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Integer zipcode = 100;
        String address = null;
        String addressDetail = "강남구";
        String message = "배송전 연락바랍니다.";

        // when && then
        assertThatThrownBy(() -> ShippingInfo.builder()
            .zipcode(zipcode)
            .address(address)
            .addressDetail(addressDetail)
            .message(message)
            .build()
        )
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공백인_상세_주소_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Integer zipcode = 100;
        String address = "Seoul";
        String addressDetail = "            ";
        String message = "배송전 연락바랍니다.";

        // when && then
        assertThatThrownBy(() -> ShippingInfo.builder()
            .zipcode(zipcode)
            .address(address)
            .addressDetail(addressDetail)
            .message(message)
            .build()
        )
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void NULL인_상세_주소_값을_사용하여_객체를_생성하는_경우_예외가_발생한다() {

        // given
        Integer zipcode = 100;
        String address = "Seoul";
        String addressDetail = null;
        String message = "배송전 연락바랍니다.";

        // when && then
        assertThatThrownBy(() -> ShippingInfo.builder()
            .zipcode(zipcode)
            .address(address)
            .addressDetail(addressDetail)
            .message(message)
            .build()
        )
            .isInstanceOf(IllegalArgumentException.class);
    }
}