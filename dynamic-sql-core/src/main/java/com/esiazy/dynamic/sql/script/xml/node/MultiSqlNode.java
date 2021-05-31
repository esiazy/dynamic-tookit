package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;

import java.util.List;

/**
 * @author wxf
 * @date 2021/5/24 16:15
 */
public class MultiSqlNode implements SqlNode {
    private final List<SqlNode> sqlNodeList;

    public MultiSqlNode(List<SqlNode> sqlNodeList) {
        this.sqlNodeList = sqlNodeList;
    }

    @Override
    public boolean build(DynamicSqlContext context) {
        sqlNodeList.forEach(l -> l.build(context));
        return true;
    }
}
