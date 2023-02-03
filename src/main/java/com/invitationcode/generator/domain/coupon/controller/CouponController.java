package com.invitationcode.generator.domain.coupon.controller;

import com.invitationcode.generator.domain.coupon.dto.CouponCreateRequestDto;
import com.invitationcode.generator.domain.coupon.dto.CouponGivenRequestDto;
import com.invitationcode.generator.domain.coupon.dto.CouponUpdateRequestDto;
import com.invitationcode.generator.domain.coupon.service.CouponService;
import com.invitationcode.generator.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.invitationcode.generator.global.response.ResponseStatus.CODE_200;
import static com.invitationcode.generator.global.response.ResponseStatus.CODE_201;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public BaseResponse<Long> create(@Valid @RequestBody CouponCreateRequestDto requestDto) {
        Long response = couponService.create(requestDto);
        return new BaseResponse<>(CODE_201, response);
    }

    @PutMapping("/{couponIdx}")
    public BaseResponse<Long> update(@PathVariable(value = "couponIdx") Long couponIdx, @Valid @RequestBody CouponUpdateRequestDto requestDto) {
        Long response = couponService.update(couponIdx, requestDto);
        return new BaseResponse<>(CODE_200, response);
    }

    @PostMapping("/{couponIdx}/given")
    public BaseResponse<Long> given(@PathVariable(value = "couponIdx") Long couponIdx, @Valid @RequestBody CouponGivenRequestDto requestDto) {
        Long response = couponService.given(couponIdx, requestDto);
        return new BaseResponse<>(CODE_201, response);
    }
}
