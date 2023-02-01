package com.invitationcode.generator.domain.orders.domain;

import com.invitationcode.generator.domain.member.domain.Member;
import com.invitationcode.generator.domain.orderslist.domain.OrdersList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
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
    @JoinColumn(name = "member_idx", foreignKey = @ForeignKey(name = "orders_member"))
    private Member member;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersList> ordersLists = new ArrayList<>();
}
