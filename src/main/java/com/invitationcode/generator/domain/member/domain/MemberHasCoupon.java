package com.invitationcode.generator.domain.member.domain;

import com.invitationcode.generator.domain.coupon.domain.Coupon;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member_has_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberHasCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment(value = "쿠폰명")
    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Comment(value = "쿠폰 할인 금액")
    @Column(name = "coupon_sale_price", nullable = false)
    private BigDecimal couponSalePrice;

    @Comment(value = "쿠폰 재고")
    @Column(name = "coupon_stock", nullable = false)
    private Integer couponStock;

    @Comment(value = "쿠폰 만료일자")
    @Column(name = "coupon_expiration_datetime", nullable = false)
    private LocalDateTime couponExpirationDateTime;

    @Comment(value = "생성일자")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(name = "fk_member_has_coupon_member"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_idx", nullable = false, foreignKey = @ForeignKey(name = "fk_member_has_coupon_coupon"))
    private Coupon coupon;

    @OneToOne(mappedBy = "memberHasCoupon", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MemberUsedCouponHistory memberUsedCouponHistory;

    @Builder
    public MemberHasCoupon(Member member, Coupon coupon, String couponName, BigDecimal couponSalePrice, Integer couponStock, LocalDateTime couponExpirationDateTime) {
        this.member = member;
        this.coupon = coupon;
        this.couponName = couponName;
        this.couponSalePrice = couponSalePrice;
        this.couponStock = couponStock;
        this.couponExpirationDateTime = couponExpirationDateTime;
        this.createdDate = LocalDateTime.now();
    }

    public void usedCoupon(Integer usedCouponStock) {
        verifyCouponDateExpiration();
        decreaseCouponStock(usedCouponStock);
        createMemberUsedCouponHistory(usedCouponStock);
    }

    private void verifyCouponDateExpiration() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(this.couponExpirationDateTime)) {
            throw new BusinessException(ErrorCode.COUPON_ALREADY_EXPIRATION_DATETIME_EXCEPTION);
        }
    }

    private void decreaseCouponStock(Integer usedCouponStock) {
        if (usedCouponStock == null || usedCouponStock < 0 || this.couponStock < usedCouponStock) {
            throw new BusinessException(ErrorCode.BIG_REQUEST_STOCK_THAN_COUPON_STOCK_EXCEPTION);
        }
        this.couponStock -= usedCouponStock;
    }

    private void createMemberUsedCouponHistory(Integer usedCouponStock) {
        this.memberUsedCouponHistory = MemberUsedCouponHistory.builder()
                .couponUsedStock(usedCouponStock)
                .memberHasCoupon(this)
                .build();
    }
}
