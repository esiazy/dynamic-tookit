package com.esiazy.dynamic.sql.exceptions;

/**
 * @author wxf
 * @date 2021/5/26 14:33
 */
public class TooManyResultException extends DynamicSqlException {
    public TooManyResultException() {
    }

    public TooManyResultException(String message) {
        super(message);
    }

    public TooManyResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyResultException(Throwable cause) {
        super(cause);
    }

    public TooManyResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
