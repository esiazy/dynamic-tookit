package com.esiazy.dynamic.code.exception;

/**
 * @author wxf
 * @date 2021/5/29 14:20
 */
public class DynamicException extends Exception {
    public DynamicException() {
    }

    public DynamicException(String message) {
        super(message);
    }

    public DynamicException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicException(Throwable cause) {
        super(cause);
    }

    public DynamicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
