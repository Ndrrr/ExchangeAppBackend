package com.exchange.app.handler.errors;

import com.exchange.app.handler.BaseException;

public class DateException extends BaseException {

    public DateException(String code, String message) {
        super(code, message);
    }
}
