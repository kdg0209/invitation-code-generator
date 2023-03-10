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

    @Comment(value = "μν κΈμ‘")
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
