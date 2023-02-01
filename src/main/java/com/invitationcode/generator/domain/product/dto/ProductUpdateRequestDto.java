package com.invitationcode.generator.domain.product.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class ProductUpdateRequestDto {

    @NotNull
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    @Positive
    private Integer stock;
}
