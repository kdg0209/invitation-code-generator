package com.invitationcode.generator.global.response;

import lombok.Getter;

import static com.invitationcode.generator.global.utils.MessageSourceUtils.getMessage;

@Getter
public class BaseResponse<T> {

    private Integer status;
    private T result;
    private String resultMessage;

    public BaseResponse(ResponseStatus status, T result) {
        this.status = BaseHttpStatusResponse.getHttpStatus(status);
        this.result = result;
        this.resultMessage = getMessage(status);
    }
}
