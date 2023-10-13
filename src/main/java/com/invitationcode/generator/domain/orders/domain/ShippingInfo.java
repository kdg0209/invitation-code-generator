package com.invitationcode.generator.domain.orders.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 배송 정보 클래스
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo {

    @Comment(value = "우편 번호")
    @Column(name = "zipcode", nullable = false)
    private int zipcode;

    @Comment(value = "주소")
    @Column(name = "address", nullable = false)
    private String address;

    @Comment(value = "상세 주소")
    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Comment(value = "배송 요청 사항")
    @Column(name = "message")
    private String message;

    @Builder
    public ShippingInfo(int zipcode, String address, String addressDetail, String message) {
        setZipcode(zipcode);
        setAddress(address);
        setAddressDetail(addressDetail);
        this.message = message;
    }

    private void setZipcode(int zipcode) {
        if (zipcode < 0) {
            throw new IllegalArgumentException();
        }
        this.zipcode = zipcode;
    }

    private void setAddress(String address) {
        if (!StringUtils.hasText(address)) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    private void setAddressDetail(String addressDetail) {
        if (!StringUtils.hasText(addressDetail)) {
            throw new IllegalArgumentException();
        }
        this.addressDetail = addressDetail;
    }
}
