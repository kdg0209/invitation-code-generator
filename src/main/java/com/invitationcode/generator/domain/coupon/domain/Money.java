package com.invitationcode.generator.domain.coupon.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Comment(value = "할인 금액")
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    public Money(Integer salePrice) {
        setSalePrice(salePrice);
    }

    private void setSalePrice(Integer salePrice) {
        if (salePrice == null || salePrice < 0) {
            throw new IllegalArgumentException();
        }
        this.salePrice = new BigDecimal(salePrice);
    }
}



