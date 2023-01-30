package com.invitationcode.generator.global.exception;

public enum ErrorCode {

    DUPLICATED_MEMBER_ID("DUPLICATED_MEMBER_ID"),
    MEMBER_NOT_FOUND_EXCEPTION("MEMBER_NOT_FOUND_EXCEPTION"),
    ;

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
