package com.esiazy.dynamic.code.invoker;

import com.esiazy.dynamic.code.exception.ExecutorException;

import java.lang.reflect.Method;

/**
 * @author wxf
 * @date 2021/5/29 14:44
 */
public interface Invoker {
    /**
     * 反射执行器
     *
     * @param obj    对象
     * @param method 方法
     * @param args   参数
     * @return 结果
     * @throws ExecutorException 执行异常
     */
    Object invoker(Object obj, Method method, Object... args) throws ExecutorException;
}
