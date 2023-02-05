package com.invitationcode.generator.domain.orders.domain;

import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class OrdersMoney {
    public static final OrdersMoney ZERO = new OrdersMoney();
    private static final int DEFAULT_MONEY = 0;

    @Comment(value = "총 결제 금액")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public OrdersMoney() {
        this.totalPrice = new BigDecimal(DEFAULT_MONEY);
    }

    public OrdersMoney(BigDecimal totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersMoney(Integer totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersMoney plus(BigDecimal totalPrice) {
        return new OrdersMoney(this.totalPrice.add(totalPrice));
    }

    public OrdersMoney minus(OrdersMoney discountOrdersMoney) {
        return new OrdersMoney(this.totalPrice.subtract(discountOrdersMoney.totalPrice));
    }

    public boolean isThanEqual(OrdersMoney productOrdersMoney) {
        return this.totalPrice.compareTo(productOrdersMoney.totalPrice) == 0;
    }

    public boolean isLessThan(OrdersMoney productOrdersMoney) {
        return this.totalPrice.compareTo(productOrdersMoney.totalPrice) < 0;
    }

    public boolean isThanGreater(OrdersMoney productOrdersMoney) {
        return this.totalPrice.compareTo(productOrdersMoney.totalPrice) > 0;
    }

    private void setTotalPrice(Integer totalPrice) {
        if (totalPrice == null || totalPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = new BigDecimal(totalPrice);
    }

    private void setTotalPrice(BigDecimal totalPrice) {
        if (totalPrice == null) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = totalPrice;
    }
}
