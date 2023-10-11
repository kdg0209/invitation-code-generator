package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class CouponUsedRequest {

    @Positive
    private long hasCouponIdx;

    @Positive
    private int usedStock;
}
