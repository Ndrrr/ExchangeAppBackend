package com.exchange.app.handler;

public enum ErrorCode {

    INVALID_CREDENTIALS,
    INVALID_TOKEN,
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    MANDATORY_FIELD_NOT_DEFINED,
    CURRENCY_NOT_FOUND,

    CURRENCY_CONVERTING_FAILED,
    EMAIL_SENDING_ERROR;

    public String code() {
        return name();
    }

}
