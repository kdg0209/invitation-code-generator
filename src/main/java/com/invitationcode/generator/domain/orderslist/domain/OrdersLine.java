package com.invitationcode.generator.domain.orderslist.domain;

import com.invitationcode.generator.domain.orders.domain.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders_line")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Embedded
    private PurchaseMoney purchaseMoney;

    @Comment(value = "상품 번호")
    @Column(name = "product_idx", nullable = false)
    private Long productIdx; // FK를 설정하지 않은 이유 : 구매 목록과 상품의 연관관계를 설정해주기 싫음, 필요하다면 JOIN을 사용하여 가져옴

    @Comment(value = "상품명")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Comment(value = "구매 수량")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_idx", foreignKey = @ForeignKey(name = "fk_ordersList_orders"))
    private Orders orders;

    @Builder
    public OrdersLine(Orders orders, Long productIdx, BigDecimal purchaseMoney, String productName, int quantity) {
        this.orders = orders;
        this.productIdx = productIdx;
        this.purchaseMoney = new PurchaseMoney(purchaseMoney);
        this.productName = productName;
        this.quantity = quantity;
    }
}
