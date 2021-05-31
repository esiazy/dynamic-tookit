package com.esiazy.dynamic.sql.source.sqlsource;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.script.xml.node.SqlNode;
import com.esiazy.dynamic.sql.source.BoundSql;
import com.esiazy.dynamic.sql.source.SqlSource;
import com.esiazy.dynamic.sql.util.parser.SqlSourceParser;

/**
 * @author wxf
 * @date 2021/5/25 9:35
 */
public final class DynamicSqlSource implements SqlSource {
    private final SqlNode sqlNode;

    public DynamicSqlSource(SqlNode sql) {
        this.sqlNode = sql;
    }

    @Override
    public BoundSql getBoundSql(MetaHashMap param) {
        DynamicSqlContext context = new DynamicSqlContext(param);
        sqlNode.build(context);
        String sql = context.getSql();
        SqlSourceParser parser = new SqlSourceParser();
        SqlSource parse = parser.parseSharp(sql, param);
        return parse.getBoundSql(param);
    }
}
