package com.invitationcode.generator.domain.coupon.service;

import com.invitationcode.generator.domain.coupon.dao.CouponDao;
import com.invitationcode.generator.domain.coupon.domain.Coupon;
import com.invitationcode.generator.domain.coupon.domain.Money;
import com.invitationcode.generator.domain.coupon.dto.CouponCreateRequestDto;
import com.invitationcode.generator.domain.coupon.dto.CouponUpdateRequestDto;
import com.invitationcode.generator.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponDao couponDao;
    private final CouponRepository couponRepository;

    public Long create(CouponCreateRequestDto requestDto) {
        Coupon coupon = Coupon.builder()
                .name(requestDto.getName())
                .stock(requestDto.getStock())
                .expirationDateTime(requestDto.getExpirationDateTime())
                .money(new Money(requestDto.getPrice()))
                .build();

        couponRepository.save(coupon);
        return coupon.getIdx();
    }

    public Long update(Long couponIdx, CouponUpdateRequestDto requestDto) {
        Coupon coupon = couponDao.findByIdx(couponIdx);
        coupon.updateName(requestDto.getName());
        coupon.updateMoney(new Money(requestDto.getPrice()));
        coupon.updateStock(requestDto.getStock());
        coupon.updateExpirationDateTime(requestDto.getExpirationDateTime());

        return coupon.getIdx();
    }
}
