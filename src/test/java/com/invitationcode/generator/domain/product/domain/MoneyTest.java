package com.invitationcode.generator.domain.product.domain;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MoneyTest {

    @Test
    void 정상적인_Money_클래스_생성_1() {

        // given
        int price = 1050;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // when
        String format = decimalFormat.format(price);

        // then
        assertThat(format).isEqualTo("1,050");
    }

    @Test
    void 정상적인_Money_클래스_생성_2() {

        // given
        int price = 100050;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // when
        String format = decimalFormat.format(price);

        // then
        assertThat(format).isEqualTo("100,050");
    }

    @Test
    void 정상적인_Money_클래스_생성_3() {

        // given
        int price = 1205050;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // when
        String format = decimalFormat.format(price);

        // then
        assertThat(format).isEqualTo("1,205,050");
    }
}