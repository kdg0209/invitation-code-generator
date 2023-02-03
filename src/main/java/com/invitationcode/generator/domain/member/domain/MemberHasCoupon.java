package com.invitationcode.generator.domain.member.domain;

import com.invitationcode.generator.domain.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_has_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberHasCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "재고")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(name = "fk_member_has_coupon_member"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_idx", nullable = false, foreignKey = @ForeignKey(name = "fk_member_has_coupon_coupon"))
    private Coupon coupon;

    @Comment(value = "사용일자")
    @Column(name = "used_date")
    private LocalDateTime usedDate;

    @Comment(value = "생성일자")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public MemberHasCoupon(Member member, Coupon coupon, Integer stock) {
        this.member = member;
        this.coupon = coupon;
        this.stock = stock;
        this.createdDate = LocalDateTime.now();
    }
}
