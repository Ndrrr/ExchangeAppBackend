package com.exchange.app.error;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String code, String message) {
        super(code, message);
    }

}
