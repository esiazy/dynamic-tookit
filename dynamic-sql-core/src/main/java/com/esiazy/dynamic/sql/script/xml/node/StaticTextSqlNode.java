package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;

/**
 * 静态文本sql节点
 *
 * @author wxf
 */
public class StaticTextSqlNode implements SqlNode {

    private final String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean build(DynamicSqlContext context) {
        context.appendSql(text);
        return true;
    }
}