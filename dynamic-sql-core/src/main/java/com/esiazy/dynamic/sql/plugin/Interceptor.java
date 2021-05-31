package com.esiazy.dynamic.sql.plugin;


/**
 * @author wxf
 */
public interface Interceptor {

    default Object plugin(Object target) {
        return target;
    }
}