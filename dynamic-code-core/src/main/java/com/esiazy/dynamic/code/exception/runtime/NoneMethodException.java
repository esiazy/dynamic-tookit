package com.esiazy.dynamic.code.exception.runtime;

/**
 * 路由错误
 *
 * @author wxf
 * @date 2021/4/15 20:24
 */
public class NoneMethodException extends DynamicRuntimeException {

    public NoneMethodException() {
        super();
    }

    public NoneMethodException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
