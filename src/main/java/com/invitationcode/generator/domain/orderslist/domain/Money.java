package com.invitationcode.generator.domain.orderslist.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Comment(value = "상품 금액")
    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    public Money(Integer productPrice) {
        setProductPrice(productPrice);
    }

    private void setProductPrice(Integer productPrice) {
        if (productPrice == null || productPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.productPrice = new BigDecimal(productPrice);
    }
}