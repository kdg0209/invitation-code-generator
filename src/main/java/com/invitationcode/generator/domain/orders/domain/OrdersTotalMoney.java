package com.invitationcode.generator.domain.orders.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Embeddable
public class OrdersTotalMoney {

    public static final OrdersTotalMoney ZERO = new OrdersTotalMoney();
    private static final int DEFAULT_MONEY = 0;

    @Comment(value = "총 결제 금액")
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    private OrdersTotalMoney() {
        this.totalPrice = new BigDecimal(DEFAULT_MONEY);
    }

    public OrdersTotalMoney(BigDecimal totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersTotalMoney(long totalPrice) {
        setTotalPrice(totalPrice);
    }

    public OrdersTotalMoney plus(BigDecimal totalPrice) {
        return new OrdersTotalMoney(this.totalPrice.add(totalPrice));
    }

    public OrdersTotalMoney minus(BigDecimal totalPrice) {
        return new OrdersTotalMoney(this.totalPrice.subtract(totalPrice));
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
