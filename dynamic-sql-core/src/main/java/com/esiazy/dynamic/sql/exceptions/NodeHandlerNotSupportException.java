package com.esiazy.dynamic.sql.exceptions;

/**
 * sql标签不支持异常
 *
 * @author wxf
 * @date 2021/5/26 14:30
 */
public class NodeHandlerNotSupportException extends DynamicSqlException {
    public NodeHandlerNotSupportException() {
    }

    public NodeHandlerNotSupportException(String message) {
        super(message);
    }

    public NodeHandlerNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeHandlerNotSupportException(Throwable cause) {
        super(cause);
    }

    public NodeHandlerNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
