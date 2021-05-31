package com.esiazy.dynamic.sql.core.starter;

import com.esiazy.dynamic.sql.plugin.Interceptor;

/**
 * @author wxf
 * @date 2021/5/28 16:18
 */
public class InterceptorConfig {
    private Interceptor[] interceptors;

    public InterceptorConfig(Interceptor... interceptors) {
        this.interceptors = interceptors;
    }

    public Interceptor[] getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(Interceptor... interceptors) {
        this.interceptors = interceptors;
    }
}
