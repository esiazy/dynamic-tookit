package com.esiazy.dynamic.sql.util.ognl;

import java.math.BigDecimal;

/**
 * 表达式分析
 *
 * @author wxf
 * @date 2021/5/28 9:32
 */
public final class ExpressionEvaluator {

    /**
     * 分析表达式是否成立
     *
     * @param expression      表达式参考Ognl
     * @param parameterObject 参数
     * @return 结果
     */
    public static boolean evaluateBoolean(String expression, Object parameterObject) {
        Object value = OgnlCache.getValue(expression, parameterObject);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
        }
        return value != null;
    }

}
