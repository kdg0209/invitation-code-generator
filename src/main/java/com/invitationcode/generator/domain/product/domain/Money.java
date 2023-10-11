package com.invitationcode.generator.domain.product.domain;

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

    @Comment(value = "상품 금액")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Money(long price) {
        setPrice(price);
    }

    private void setPrice(long price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }
        this.price = new BigDecimal(price);
    }
}
