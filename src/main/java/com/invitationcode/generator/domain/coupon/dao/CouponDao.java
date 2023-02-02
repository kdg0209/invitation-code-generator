package com.invitationcode.generator.domain.coupon.dao;

import com.invitationcode.generator.domain.coupon.domain.Coupon;
import com.invitationcode.generator.global.exception.BusinessException;
import com.invitationcode.generator.global.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.invitationcode.generator.domain.coupon.domain.QCoupon.coupon;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponDao {

    private final JPAQueryFactory queryFactory;

    public Coupon findByIdx(Long couponIdx) {
        Coupon result = queryFactory
                .selectFrom(coupon)
                .where(
                        coupon.idx.eq(couponIdx)
                )
                .fetchOne();

        return Optional.ofNullable(result)
                .orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND_EXCEPTION));
    }
}
