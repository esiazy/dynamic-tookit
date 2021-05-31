package com.esiazy.dynamic.sql.exceptions;

/**
 * sql目标对象路由失败
 *
 * @author wxf
 * @date 2021/5/26 15:39
 */
public class TargetRoutingException extends DynamicSqlException {
    public TargetRoutingException() {
    }

    public TargetRoutingException(String message) {
        super(message);
    }

    public TargetRoutingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TargetRoutingException(Throwable cause) {
        super(cause);
    }

    public TargetRoutingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
