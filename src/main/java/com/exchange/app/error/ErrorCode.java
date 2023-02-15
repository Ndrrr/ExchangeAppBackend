package com.exchange.app.error;

public enum ErrorCode {

    INVALID_CREDENTIALS,
    USER_NOT_FOUND;

    public String code() {
        return name();
    }

}
