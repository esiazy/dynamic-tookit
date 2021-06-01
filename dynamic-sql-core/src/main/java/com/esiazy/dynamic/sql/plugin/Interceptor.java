package com.esiazy.dynamic.sql.plugin;

import java.lang.reflect.Method;

/**
 * @author wxf
 */
public interface Interceptor {

    Object intercept(Object proxy, Method method, Object[] args) throws Throwable;

    default Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}