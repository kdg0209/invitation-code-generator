package com.invitationcode.generator.domain.orders.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Money {
    public static final Money ZERO = new Money();
    private static final int DEFAULT_MONEY = 0;

    @Comment(value = "총 결제 금액")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public Money() {
        this.totalPrice = new BigDecimal(DEFAULT_MONEY);
    }

    public Money(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Money(Integer totalPrice) {
        setTotalPrice(totalPrice);
    }

    public Money plus(BigDecimal totalPrice) {
        return new Money(this.totalPrice.add(totalPrice));
    }

    public boolean isThanEqual(Money productMoney) {
        return this.totalPrice.compareTo(productMoney.totalPrice) == 0;
    }

    public boolean isLessThan(Money productMoney) {
        return this.totalPrice.compareTo(productMoney.totalPrice) < 0;
    }

    public boolean isThanGreater(Money productMoney) {
        return this.totalPrice.compareTo(productMoney.totalPrice) > 0;
    }

    private void setTotalPrice(Integer totalPrice) {
        if (totalPrice == null || totalPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = new BigDecimal(totalPrice);
    }
}
