package com.invitationcode.generator.domain.orders.domain;

import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.orderslist.domain.OrdersLine;
import com.invitationcode.generator.domain.orderslist.domain.PurchaseMoney;
import com.invitationcode.generator.domain.product.domain.Product;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Embedded
    private Money money;

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
    public Orders(Member member, Integer depositPrice, ShippingInfo shippingInfo) {
        this.member = member;
        this.money = new Money(depositPrice);
        this.shippingInfo = shippingInfo;
    }

    public Long getIdx() {
        return idx;
    }

    /**
     * @return 상품 구매 금액 * 구매한 상품의 갯수
     * @descriptopn 주문한 상품을 구매 목록에 담고 구매한 상품의 총 금액을 반환하는 메서드
     */
    public PurchaseMoney addOrdersLineAndReturnPurchaseTotalMoney(Product product, Integer productBuyQuantity) {
        product.decreaseStock(productBuyQuantity);
        OrdersLine ordersLine = OrdersLine.builder()
                .orders(this)
                .productIdx(product.getIdx())
                .purchaseMoney(product.getMoney())
                .productName(product.getName())
                .productBuyQuantity(productBuyQuantity)
                .build();
        this.ordersLines.add(ordersLine);

        return ordersLine.purchaseTotalMoney();
    }

    public void statusChangeByPurchase(Money totalPurchaseMoney) {
        if (this.money.isThanEqual(totalPurchaseMoney)) {
            this.status = OrderStatus.PAY_COMPLETED;
        }
        if (this.money.isLessThan(totalPurchaseMoney)) {
            this.status = OrderStatus.PAY_WAITING;
        }
        if (this.money.isThanGreater(totalPurchaseMoney)) {
            throw new BusinessException(ErrorCode.TOTAL_PRICE_THAN_PRODUCT_MONEY_EXCEPTION);
        }
    }
}
