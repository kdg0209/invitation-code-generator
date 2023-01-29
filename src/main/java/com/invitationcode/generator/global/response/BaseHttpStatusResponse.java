package com.invitationcode.generator.global.response;

public enum BaseHttpStatusResponse {

    CODE_200(200),
    CODE_201(201),
    CODE_400(400),
    CODE_401(401),
    CODE_403(403),
    CODE_404(404),
    CODE_405(405),
    CODE_500(500);

    private Integer status;

    BaseHttpStatusResponse(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public static Integer getHttpStatus(ResponseStatus status) {
        switch (status) {
            case CODE_200:
                return CODE_200.getStatus();
            case CODE_201:
                return CODE_201.getStatus();
            case CODE_400:
                return CODE_400.getStatus();
            case CODE_401:
                return CODE_401.getStatus();
            case CODE_403:
                return CODE_403.getStatus();
            case CODE_404:
                return CODE_404.getStatus();
            case CODE_405:
                return CODE_405.getStatus();
            case CODE_500:
                return CODE_500.getStatus();
            default:
                throw new IllegalArgumentException();
        }
    }
}
