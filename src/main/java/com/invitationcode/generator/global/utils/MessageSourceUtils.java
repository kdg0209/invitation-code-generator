package com.invitationcode.generator.global.utils;

import com.invitationcode.generator.global.exception.ErrorCode;
import com.invitationcode.generator.global.response.ResponseStatus;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public final class MessageSourceUtils {

    @Resource
    private MessageSource source;

    static MessageSource messageSource;

    @PostConstruct
    public void initialize() {
        System.out.println("call initialize");
        System.out.println("source class : " + source.getClass());
        messageSource = source;
        System.out.println("messageSource class : " + messageSource.getClass());
    }

    public static String getMessage(ResponseStatus responseStatus) {
        return messageSource.getMessage(responseStatus.getStatus(), null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(ErrorCode errorCode) {
        return messageSource.getMessage(errorCode.getCode(), null, LocaleContextHolder.getLocale());
    }
}
