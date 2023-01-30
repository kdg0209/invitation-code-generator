package com.invitationcode.generator.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.invitationcode.generator.global.utils.MessageSourceUtils.getMessage;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private Integer status;
    private T result;
    private String resultMessage;

    public BaseResponse(ResponseStatus status) {
        this.status = BaseHttpStatusResponse.getHttpStatus(status);
        this.resultMessage = getMessage(status);
    }

    public BaseResponse(ResponseStatus status, T result) {
        this.status = BaseHttpStatusResponse.getHttpStatus(status);
        this.result = result;
        this.resultMessage = getMessage(status);
    }
}
