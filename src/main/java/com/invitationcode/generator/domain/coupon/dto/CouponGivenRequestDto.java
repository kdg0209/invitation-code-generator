package com.invitationcode.generator.domain.coupon.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CouponGivenRequestDto {

    @NotNull
    @Positive
    private Long memberIdx;

    @NotNull
    @Positive
    private Integer stock;
}
