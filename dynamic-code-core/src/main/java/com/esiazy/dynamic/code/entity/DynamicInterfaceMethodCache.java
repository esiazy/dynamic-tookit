package com.esiazy.dynamic.code.entity;

import java.lang.reflect.Method;

/**
 * @author wxf
 * @date 2021/5/29 11:00
 */
public class DynamicInterfaceMethodCache {
    private Method method;

    private Class<?>[] methodParamTypes;

    public DynamicInterfaceMethodCache(Method method, Class<?>[] methodParamTypes) {
        this.method = method;
        this.methodParamTypes = methodParamTypes;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(Class<?>[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }
}
