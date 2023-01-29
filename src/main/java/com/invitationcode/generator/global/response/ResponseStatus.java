package com.invitationcode.generator.global.response;

public enum ResponseStatus {

    CODE_200("CODE_200"),
    CODE_201("CODE_201"),
    CODE_400("CODE_400"),
    CODE_401("CODE_401"),
    CODE_403("CODE_403"),
    CODE_404("CODE_404"),
    CODE_405("CODE_405"),
    CODE_500("CODE_500");

    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
