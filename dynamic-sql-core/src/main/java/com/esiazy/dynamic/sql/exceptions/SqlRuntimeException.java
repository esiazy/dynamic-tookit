package com.esiazy.dynamic.sql.exceptions;

/**
 * sql异常转运行时异常
 *
 * @author wxf
 * @date 2021/5/26 14:32
 * @see java.sql.SQLException
 */
public class SqlRuntimeException extends DynamicSqlException {
    public SqlRuntimeException() {
    }

    public SqlRuntimeException(String message) {
        super(message);
    }

    public SqlRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlRuntimeException(Throwable cause) {
        super(cause);
    }

    public SqlRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
