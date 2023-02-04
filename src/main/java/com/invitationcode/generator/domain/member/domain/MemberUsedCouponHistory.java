package com.invitationcode.generator.domain.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_used_coupon_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUsedCouponHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "사용한 재고")
    @Column(name = "coupon_used_stock", nullable = false)
    private Integer couponUsedStock;

    @Comment(value = "사용일자")
    @Column(name = "coupon_used_date")
    private LocalDateTime couponUsedDate;

    @OneToOne
    @JoinColumn(name = "member_has_coupon_idx", foreignKey = @ForeignKey(name = "fk_member_used_coupon_history_member_has_coupon"))
    private MemberHasCoupon memberHasCoupon;
}
