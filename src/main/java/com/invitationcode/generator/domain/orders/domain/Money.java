package com.invitationcode.generator.domain.orders.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Comment(value = "총 결제 금액")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public Money(Integer totalPrice) {
        setTotalPrice(totalPrice);
    }

    private void setTotalPrice(Integer totalPrice) {
        if (totalPrice == null || totalPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = new BigDecimal(totalPrice);
    }
}
