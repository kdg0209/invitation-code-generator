package com.invitationcode.generator.domain.orderslist.domain;

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
public class PurchaseMoney {

    @Comment(value = "상품 구매 금액")
    @Column(name = "purchase_money", nullable = false)
    private BigDecimal purchaseMoney;

    public PurchaseMoney(BigDecimal purchaseMoney) {
        setPurchaseMoney(purchaseMoney);
    }

    private void setPurchaseMoney(BigDecimal purchaseMoney) {
        if (purchaseMoney == null || purchaseMoney.signum() == -1) {
            throw new IllegalArgumentException();
        }
        this.purchaseMoney = purchaseMoney;
    }
}