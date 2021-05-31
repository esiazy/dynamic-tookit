package com.esiazy.dynamic.sql.exceptions;

/**
 * @author Esadmin
 * @date 2021/5/28 9:25
 */
public class BuilderException extends DynamicSqlException {
    public BuilderException() {
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }

    public BuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
