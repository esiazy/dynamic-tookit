package com.esiazy.dynamic.code.invoker;

import com.esiazy.dynamic.code.exception.ExecutorException;

import java.lang.reflect.Method;

/**
 * DynamicInvoker
 *
 * @author wxf
 * @date 2021/5/29 14:29
 */
public class DynamicInvoker implements Invoker {

    @Override
    public Object invoker(Object obj, Method method, Object... args) throws ExecutorException {
        try {
            return method.invoke(obj, args);
        } catch (Throwable throwable) {
            throw new ExecutorException("executor method named [" + method.getName() + "] error : e ", throwable);
        }
    }

}
