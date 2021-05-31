package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.exceptions.BuilderException;
import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;
import com.esiazy.dynamic.sql.util.parser.GenericTokenParser;
import com.esiazy.dynamic.sql.util.parser.TokenHandler;

import java.util.Map;

/**
 * 文本节点
 * 替换${}
 * <p>判断是否为动态sql</p>
 *
 * @author wxf
 * @date 2021/5/28 9:38
 */
public class TextSqlNode implements SqlNode {
    private final String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    public boolean isDynamic() {
        return isDynamic(text);
    }

    @Override
    public boolean build(DynamicSqlContext context) {
        context.appendSql(parseDollar(text, context.getParams()));
        return true;
    }

    public boolean isDynamic(String originalSql) {
        DynamicTokenHandler handler = new DynamicTokenHandler(null);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        parser.parse(originalSql);
        return handler.isDynamic;
    }

    public String parseDollar(String originalSql, Map<String, Object> params) {
        DynamicTokenHandler handler = new DynamicTokenHandler(params);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        return parser.parse(originalSql);
    }

    public static class DynamicTokenHandler implements TokenHandler {
        private boolean isDynamic;

        private Map<String, Object> params;

        public DynamicTokenHandler() {
        }

        public DynamicTokenHandler(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public String handleToken(String content) {
            this.isDynamic = true;
            if (params != null) {
                Object value = params.get(content);
                if (value == null) {
                    throw new BuilderException("args named[" + content + "] is not found in params.");
                }
                return value.toString();
            }
            return null;
        }
    }
}
