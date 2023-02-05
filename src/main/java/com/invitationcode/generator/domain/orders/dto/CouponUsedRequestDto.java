package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CouponUsedRequestDto {

    @NotNull
    @Positive
    private Long hasCouponIdx;

    @NotNull
    @Positive
    private Integer usedStock;
}
