package com.esiazy.dynamic.sql.util.ognl;

import com.esiazy.dynamic.sql.exceptions.BuilderException;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxf
 * @date 2021/5/28 9:24
 */
public class OgnlCache {

    private static final Map<String, Object> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    private OgnlCache() {
    }

    public static Object getValue(String expression, Object root) {
        try {
            return Ognl.getValue(parseExpression(expression), root);
        } catch (OgnlException e) {
            throw new BuilderException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
        }
    }

    private static Object parseExpression(String expression) throws OgnlException {
        Object node = EXPRESSION_CACHE.get(expression);
        if (node == null) {
            node = Ognl.parseExpression(expression);
            EXPRESSION_CACHE.put(expression, node);
        }
        return node;
    }
}
