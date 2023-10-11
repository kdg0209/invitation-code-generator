package com.invitationcode.generator.domain.orders.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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
    @Column(name = "message", nullable = false)
    private String message;

    @Builder
    public ShippingInfo(int zipcode, String address, String addressDetail, String message) {
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.message = message;
    }
}
