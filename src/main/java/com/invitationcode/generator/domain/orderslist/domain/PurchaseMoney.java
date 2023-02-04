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

    /**
     * @return 상품 구매 금액 * 구매한 상품의 갯수
     * @descriptopn 구매 총 합계 계산 메서드
     */
    public PurchaseMoney purchaseTotalMoney(int productBuyQuantity) {
        return new PurchaseMoney(this.purchaseMoney.multiply(BigDecimal.valueOf(productBuyQuantity)));
    }

    private void setPurchaseMoney(BigDecimal purchaseMoney) {
        if (purchaseMoney == null) {
            throw new IllegalArgumentException();
        }
        this.purchaseMoney = purchaseMoney;
    }
}