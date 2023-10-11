package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class ProductPurchaseRequest {

    @Positive
    private long productIdx;

    @Positive
    private int productBuyQuantity;
}
