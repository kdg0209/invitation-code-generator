package com.invitationcode.generator.domain.orders.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Embeddable
public class OrdersMoney {

    public static final OrdersMoney ZERO = new OrdersMoney();
    private static final int DEFAULT_MONEY = 0;

    @Comment(value = "총 결제 금액")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    private OrdersMoney() {
        this.totalPrice = new BigDecimal(DEFAULT_MONEY);
    }

    public OrdersMoney(BigDecimal totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersMoney(long totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersMoney plus(BigDecimal totalPrice) {
        return new OrdersMoney(this.totalPrice.add(totalPrice));
    }

    public OrdersMoney minus(BigDecimal totalPrice) {
        return new OrdersMoney(this.totalPrice.subtract(totalPrice));
    }

    public boolean isThanEqual(BigDecimal totalPrice) {
        return this.totalPrice.compareTo(totalPrice) == 0;
    }

    public boolean isLessThan(BigDecimal totalPrice) {
        return this.totalPrice.compareTo(totalPrice) < 0;
    }

    public boolean isThanGreater(BigDecimal totalPrice) {
        return this.totalPrice.compareTo(totalPrice) > 0;
    }

    private void setTotalPrice(long totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = new BigDecimal(totalPrice);
    }

    private void setTotalPrice(BigDecimal totalPrice) {
        if (totalPrice == null || totalPrice.signum() == -1) {
            throw new IllegalArgumentException();
        }
        this.totalPrice = totalPrice;
    }
}
