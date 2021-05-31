package com.esiazy.dynamic.sql.util.parser;

/**
 * token处理器
 *
 * @author Esadmin
 * @see GenericTokenParser
 */
public interface TokenHandler {

    /**
     * 处理
     *
     * @param content 文本
     * @return 处理后结果
     */
    String handleToken(String content);
}