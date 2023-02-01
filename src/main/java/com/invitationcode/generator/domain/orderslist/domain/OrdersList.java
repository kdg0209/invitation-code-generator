package com.invitationcode.generator.domain.orderslist.domain;

import com.invitationcode.generator.domain.orders.domain.Orders;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Embedded
    private Money money;

    @Comment(value = "상품명")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Comment(value = "구매 수량")
    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_idx", foreignKey = @ForeignKey(name = "ordersList_orders"))
    private Orders orders;

}
