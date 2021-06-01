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
            long start = System.currentTimeMillis();
            Class<?> aClass = groovyClassLoader.parseClass(code);
            log.info("parseClass success cast time :{}ms", System.currentTimeMillis() - start);
            return initObject(aClass);
        } catch (Throwable throwable) {
            throw new CompileFailErrorException("compile object error : e", throwable);
        }
    }

    /**
     * 生成并织入参数
     *
     * @param clazz clazz模板
     * @return Object 对象
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
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