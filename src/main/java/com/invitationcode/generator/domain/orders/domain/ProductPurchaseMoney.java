package com.invitationcode.generator.domain.orders.domain;

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
public class ProductPurchaseMoney {

    @Comment(value = "상품 구매 금액")
    @Column(name = "product_money", nullable = false)
    private BigDecimal productMoney;

    public ProductPurchaseMoney(BigDecimal productMoney) {
        setPurchaseMoney(productMoney);
    }

    private void setPurchaseMoney(BigDecimal productMoney) {
        if (productMoney == null || productMoney.signum() == -1) {
            throw new IllegalArgumentException();
        }
        this.productMoney = productMoney;
    }
}