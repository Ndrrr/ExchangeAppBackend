package com.exchange.app.error;

public enum ErrorCode {

    INVALID_CREDENTIALS,
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    MANDATORY_FIELD_NOT_DEFINED,
    EMAIL_SENDING_ERROR;

    public String code() {
        return name();
    }

}
