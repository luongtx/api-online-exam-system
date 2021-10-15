package com.luongtx.oes.exception;

public class ApplicationUserException extends RuntimeException {
    String message;

    public ApplicationUserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
