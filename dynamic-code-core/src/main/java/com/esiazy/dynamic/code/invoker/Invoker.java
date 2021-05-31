package com.esiazy.dynamic.code.invoker;

import com.esiazy.dynamic.code.exception.ExecutorException;

import java.lang.reflect.Method;

/**
 * @author wxf
 * @date 2021/5/29 14:44
 */
public interface Invoker {
    Object invoker(Object obj, Method method, Object... args) throws ExecutorException;
}
