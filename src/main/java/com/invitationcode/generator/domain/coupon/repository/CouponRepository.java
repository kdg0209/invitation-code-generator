package com.invitationcode.generator.domain.coupon.repository;

import com.invitationcode.generator.domain.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
