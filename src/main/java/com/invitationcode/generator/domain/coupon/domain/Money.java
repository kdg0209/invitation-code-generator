package com.invitationcode.generator.domain.coupon.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Comment(value = "할인 금액")
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    public Money(long salePrice) {
        setSalePrice(salePrice);
    }

    private void setSalePrice(long salePrice) {
        if (salePrice < 0) {
            throw new IllegalArgumentException();
        }
        this.salePrice = new BigDecimal(salePrice);
    }
}



