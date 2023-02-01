package com.invitationcode.generator.domain.orders.domain;

public enum OrderStatus {

    PAY_WAITING,          // 입금 전
    PAY_COMPLETED,        // 입금 완료
    SHIPPING_READY,       // 배송 준비중
    SHIPPING_ING,         // 배송 중
    SHIPPING_COMPLETED,   // 배송 완료
    ORDER_CANCEL,         // 주문 취소
    ORDER_RETURN,         // 반품
    ORDER_COMPLETED;      // 구매 확정
}
