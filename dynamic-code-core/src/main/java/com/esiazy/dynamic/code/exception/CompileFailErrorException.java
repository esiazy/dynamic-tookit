package com.esiazy.dynamic.code.exception;

/**
 * groovy 编译错误异常
 *
 * @author wxf
 * @date 2021/4/23 10:22
 */
public class CompileFailErrorException extends DynamicException {
    public CompileFailErrorException() {
    }

    public CompileFailErrorException(String message) {
        super(message);
    }

    public CompileFailErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileFailErrorException(Throwable cause) {
        super(cause);
    }

    public CompileFailErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
