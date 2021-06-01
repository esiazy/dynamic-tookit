package com.esiazy.dynamic.code.compile;

import com.esiazy.dynamic.code.exception.CompileFailErrorException;
import com.esiazy.dynamic.core.entity.DynamicWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 编译器根接口
 *
 * @author wxf
 * @date 2021/4/22
 */
public interface CompileService {

    /**
     * 获取编译后对象
     *
     * @param code 文本代码
     * @return 对象
     * @throws CompileFailErrorException compile
     */
    Object getInstance(String code) throws CompileFailErrorException;

    /**
     * 解析参数类型并添加到参数集合
     * <p>可自定义加载所需类型</p>
     * <p>确保顺序与方法上的参数顺序一致</p>
     *
     * @param parameterTypes 参数类型
     * @return 参数列表, 参数顺序
     */
    default List<Object> parseParameterTypes(DynamicWrapper executeDto, Class<?>[] parameterTypes) {
        List<Object> paramList = new ArrayList<>();
        //反射获取动态参数,构造动态参数
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType.isAssignableFrom(int.class)) {
                paramList.add(0);
            } else if (parameterType.isAssignableFrom(long.class)) {
                paramList.add(0L);
            } else if (parameterType.isAssignableFrom(float.class)) {
                paramList.add(0F);
            } else if (parameterType.isAssignableFrom(double.class)) {
                paramList.add(0D);
            } else if (parameterType.isAssignableFrom(char.class)) {
                paramList.add('\u0000');
            } else if (parameterType.isAssignableFrom(byte.class)) {
                paramList.add((byte) 0);
            } else if (parameterType.isAssignableFrom(short.class)) {
                paramList.add((short) 0);
            } else if (parameterType.isAssignableFrom(boolean.class)) {
                paramList.add(false);
            } else if (parameterType.isAssignableFrom(DynamicWrapper.class)) {
                paramList.add(executeDto);
            } else if (parameterType.isAssignableFrom(Map.class)) {
                paramList.add(executeDto.getParam());
            } else {
                paramList.add(null);
            }
        }
        return paramList;
    }
}
