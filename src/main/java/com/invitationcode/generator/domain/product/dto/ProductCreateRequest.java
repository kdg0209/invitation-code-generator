package com.invitationcode.generator.domain.product.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class ProductCreateRequest {

    @NotNull
    private String name;

    @Positive
    private long price;

    @Positive
    private int stock;
}
