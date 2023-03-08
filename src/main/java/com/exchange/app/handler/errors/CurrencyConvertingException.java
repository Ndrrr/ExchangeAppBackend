package com.exchange.app.handler.errors;

import com.exchange.app.handler.BaseException;

public class CurrencyConvertingException extends BaseException {

    public CurrencyConvertingException(String code, String message) {
        super(code, message);
    }
}
