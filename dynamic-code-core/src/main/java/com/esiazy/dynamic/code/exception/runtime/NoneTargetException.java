package com.esiazy.dynamic.code.exception.runtime;

/**
 * 目标不存在
 *
 * @author wxf
 * @date 2021/4/15 20:24
 */
public class NoneTargetException extends DynamicRuntimeException {
    public NoneTargetException() {
    }

    public NoneTargetException(String message) {
        super(message);
    }

    public NoneTargetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneTargetException(Throwable cause) {
        super(cause);
    }

    public NoneTargetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
