package com.invitationcode.generator.domain.orders.domain;

import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.orderslist.domain.OrdersLine;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Embedded
    private OrdersTotalMoney ordersTotalMoney;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Comment(value = "주문한 유저의 아이디")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", foreignKey = @ForeignKey(name = "fk_orders_member"))
    private Member member;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersLine> ordersLines = new ArrayList<>();

    @Builder
    public Orders(Member member, OrdersTotalMoney ordersTotalMoney, ShippingInfo shippingInfo) {
        this.member = member;
        this.ordersTotalMoney = ordersTotalMoney;
        this.shippingInfo = shippingInfo;
    }

    public void addOrdersLine(long productIdx, BigDecimal productMoney, String productName, int quantity) {
        OrdersLine ordersLine = OrdersLine.builder()
                .orders(this)
                .productIdx(productIdx)
                .purchaseMoney(productMoney)
                .productName(productName)
                .quantity(quantity)
                .build();
        this.ordersLines.add(ordersLine);
    }

    public BigDecimal purchaseTotalMoney(BigDecimal productPrice, int quantity) {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void statusChangeByPurchase(OrdersTotalMoney totalPurchaseOrdersTotalMoney) {
        BigDecimal totalPrice = totalPurchaseOrdersTotalMoney.getTotalPrice();
        if (this.ordersTotalMoney.isThanEqual(totalPrice)) {
            this.status = OrderStatus.PAY_COMPLETED;
        }
        if (this.ordersTotalMoney.isLessThan(totalPrice)) {
            this.status = OrderStatus.PAY_WAITING;
        }
        if (this.ordersTotalMoney.isThanGreater(totalPrice)) {
            throw new BusinessException(ErrorCode.TOTAL_PRICE_THAN_PRODUCT_MONEY_EXCEPTION);
        }
    }
}
