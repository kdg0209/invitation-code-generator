package com.invitationcode.generator.global.exception;

public enum ErrorCode {

    DUPLICATED_MEMBER_ID("DUPLICATED_MEMBER_ID"),
    MEMBER_NOT_FOUND_EXCEPTION("MEMBER_NOT_FOUND_EXCEPTION"),

    MEMBER_INVITATION_NOT_FOUND_EXCEPTION("MEMBER_INVITATION_NOT_FOUND_EXCEPTION"),
    MEMBER_INVITATION_ALREADY_WAITING("MEMBER_INVITATION_ALREADY_WAITING"),
    MEMBER_INVITATION_ALREADY_COMPLETED("MEMBER_INVITATION_ALREADY_COMPLETED"),
    MEMBER_INVITATION_ALREADY_REJECTED("MEMBER_INVITATION_ALREADY_REJECTED"),
    MEMBER_INVITATION_INVITE_CODE_EXPIRED("MEMBER_INVITATION_INVITE_CODE_EXPIRED"),

    PRODUCT_NOT_FOUND_EXCEPTION("PRODUCT_NOT_FOUND_EXCEPTION"),
    BIG_REQUEST_QUANTITY_THAN_PRODUCT_STOCK_EXCEPTION("BIG_REQUEST_QUANTITY_THAN_PRODUCT_STOCK_EXCEPTION"),
    TOTAL_PRICE_THAN_PRODUCT_MONEY_EXCEPTION("TOTAL_PRICE_THAN_PRODUCT_MONEY_EXCEPTION"),

    COUPON_NOT_FOUND_EXCEPTION("COUPON_NOT_FOUND_EXCEPTION"),
    COUPON_EXPIRATION_DATETIME_EXCEPTION("COUPON_EXPIRATION_DATETIME_EXCEPTION"),
    COUPON_ALREADY_EXPIRATION_DATETIME_EXCEPTION("COUPON_ALREADY_EXPIRATION_DATETIME_EXCEPTION"),
    COUPON_EXPIRATION_DATETIME_GIVEN_EXCEPTION("COUPON_EXPIRATION_DATETIME_GIVEN_EXCEPTION"),
    BIG_REQUEST_STOCK_THAN_COUPON_STOCK_EXCEPTION("BIG_REQUEST_STOCK_THAN_COUPON_STOCK_EXCEPTION"),
    ;

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
