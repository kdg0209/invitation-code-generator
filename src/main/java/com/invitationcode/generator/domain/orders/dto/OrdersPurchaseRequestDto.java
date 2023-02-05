package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrdersPurchaseRequestDto {

    @NotNull
    @Positive
    private Long memberIdx;

    @NotNull
    @Positive
    private Integer zipcode;

    @NotNull
    private String address;

    @NotNull
    private String addressDetail;

    @NotNull
    private String message;

    @NotNull
    @Positive
    private Integer depositPrice;

    @Valid
    @NotEmpty
    private List<ProductPurchaseListRequestDto> productPurchaseList;

    private List<CouponUsedRequestDto> couponUsedList;
}
