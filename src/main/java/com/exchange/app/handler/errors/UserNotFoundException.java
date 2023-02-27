package com.exchange.app.handler.errors;

import com.exchange.app.handler.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String code, String message) {
        super(code, message);
    }

}
