package com.invitationcode.generator.domain.orders.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrdersPurchaseRequest {

    @Positive
    private long memberIdx;

    @Positive
    private int zipcode;

    @NotNull
    private String address;

    @NotNull
    private String addressDetail;

    @NotNull
    private String message;

    @Positive
    private long depositPrice;

    @Valid
    @NotEmpty
    private List<ProductPurchaseRequest> productPurchaseList;

    @Valid
    private List<CouponUsedRequest> couponUsedList;
}
