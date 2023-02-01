package com.invitationcode.generator.domain.product.domain;

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
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Money(Integer price) {
        setPrice(price);
    }

    private void setPrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException();
        }
        this.price = new BigDecimal(price);
    }
}
