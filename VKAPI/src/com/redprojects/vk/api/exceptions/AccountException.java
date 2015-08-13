package com.redprojects.vk.api.exceptions;

public class AccountException extends VKAPIException {
    public AccountException(String message, int code) {
        super(message, code);
    }

    public AccountException(String message) {
        super(message);
    }
}
