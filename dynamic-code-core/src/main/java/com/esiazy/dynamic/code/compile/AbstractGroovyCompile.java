package com.esiazy.dynamic.code.compile;

import com.esiazy.dynamic.code.exception.CompileFailErrorException;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * groovy编译器实现
 *
 * @author wxf
 * @date 2021/5/7
 */
@Slf4j
public abstract class AbstractGroovyCompile implements CompileService {
    private final Pattern compile = Pattern.compile("@Value\\(\"\\$\\{(.+?)}\"\\)");

    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    @Override
    public Object getInstance(String code) throws CompileFailErrorException {
        try {
            code = preHandler(code);
            Class<?> aClass = groovyClassLoader.parseClass(code);
            Object o = initObject(aClass);
            log.info("GroovyCompile 编译成功 object:{}", o);
            return o;
        } catch (Throwable throwable) {
            throw new CompileFailErrorException("compile object error : e", throwable);
        }
    }

    /**
     * initObject
     *
     * @param clazz clazz
     * @return Object
     * @throws IllegalAccessException ill
     * @throws InstantiationException instance
     */
    protected abstract Object initObject(Class<?> clazz) throws InstantiationException, IllegalAccessException;

    /**
     * groovy编译前处理
     *
     * @param code 代码文本
     * @return 替换后
     */
    private String preHandler(String code) {
        Matcher matcher = compile.matcher(code);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(0);
            group = group.replace("$", "\\\\\\$");
            matcher.appendReplacement(sb, group);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}