package com.exchange.app.handler.errors;

import com.exchange.app.handler.BaseException;

public class CurrencyNotFoundException extends BaseException {

    public CurrencyNotFoundException(String code, String message) {
        super(code, message);
    }
}
