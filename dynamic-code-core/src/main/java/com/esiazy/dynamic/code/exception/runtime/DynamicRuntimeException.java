package com.esiazy.dynamic.code.exception.runtime;

/**
 * @author wxf
 * @date 2021/5/29 14:25
 */
public class DynamicRuntimeException extends RuntimeException {
    public DynamicRuntimeException() {
    }

    public DynamicRuntimeException(String message) {
        super(message);
    }

    public DynamicRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicRuntimeException(Throwable cause) {
        super(cause);
    }

    public DynamicRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
