package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;
import com.esiazy.dynamic.sql.util.ognl.ExpressionEvaluator;

/**
 * if节点
 * <p>ognl表达式动态添加sql语句</p>
 *
 * @author wxf
 * @see ExpressionEvaluator
 */
public class IfSqlNode implements SqlNode {

    private final String expression;

    private final MultiSqlNode sqlNodeList;

    public IfSqlNode(String expression, MultiSqlNode sqlNodeList) {
        this.expression = expression;
        this.sqlNodeList = sqlNodeList;
    }

    /**
     * {@link ExpressionEvaluator#evaluateBoolean(String, Object)} 表达式引擎
     */
    @Override
    public boolean build(DynamicSqlContext context) {
        if (!expression.isEmpty() && context.getParams() == null) {
            throw new RuntimeException("un support to null params.");
        }
        boolean evaluateBoolean = ExpressionEvaluator.evaluateBoolean(expression, context.getParams());
        if (evaluateBoolean) {
            sqlNodeList.build(context);
            return true;
        } else {
            return false;
        }
    }
}