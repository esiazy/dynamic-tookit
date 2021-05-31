package com.esiazy.dynamic.sql.exceptions;

/**
 * @author wxf
 * @date 2021/5/28 15:07
 */
public class ExecutorException extends DynamicSqlException {
    public ExecutorException() {
    }

    public ExecutorException(String message) {
        super(message);
    }

    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
