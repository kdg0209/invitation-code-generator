package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class ProductPurchaseListRequestDto {

    @NotNull
    @Positive
    private Long productIdx;

    @NotNull
    @Positive
    private Integer productBuyQuantity;
}
