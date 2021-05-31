package com.esiazy.dynamic.sql.exceptions;

/**
 * @author wxf
 * @date 2021/5/28 14:13
 */
public abstract class DynamicSqlException extends RuntimeException {
    public DynamicSqlException() {
    }

    public DynamicSqlException(String message) {
        super(message);
    }

    public DynamicSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicSqlException(Throwable cause) {
        super(cause);
    }

    public DynamicSqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
