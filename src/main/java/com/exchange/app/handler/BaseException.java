package com.exchange.app.handler;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String code;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BaseException of(ErrorCode code, String message) {
        return new BaseException(code.code(), message);
    }

}
