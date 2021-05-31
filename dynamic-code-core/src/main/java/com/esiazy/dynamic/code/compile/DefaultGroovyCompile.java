package com.esiazy.dynamic.code.compile;

/**
 * @author wxf
 * @date 2021/5/29 14:35
 */
public class DefaultGroovyCompile extends AbstractGroovyCompile {

    @Override
    protected Object initObject(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

}
