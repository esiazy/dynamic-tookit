package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * where节点
 * <p>将替换掉where节点集合中的if节点中多余的and or</p>
 *
 * @author wxf
 * @see IfSqlNode
 */
public class WhereSqlNode implements SqlNode {

    /**
     * 前缀所有可能性
     */
    private final List<String> prefixesToOverride = Arrays.asList("AND ", "OR ", "AND\n", "OR\n", "AND\r", "OR\r", "AND\t", "OR\t");

    private final MultiSqlNode sqlNodeList;

    public WhereSqlNode(MultiSqlNode sqlNode) {
        this.sqlNodeList = sqlNode;
    }

    @Override
    public boolean build(DynamicSqlContext context) {
        TrimDynamicContext trimDynamicContext = new TrimDynamicContext(context);
        boolean build = sqlNodeList.build(trimDynamicContext);
        trimDynamicContext.applyAll();
        return build;
    }

    private class TrimDynamicContext extends DynamicSqlContext {
        private final DynamicSqlContext delegate;

        private boolean prefixFirst;

        private StringBuilder sqlBuffer;

        public TrimDynamicContext(DynamicSqlContext delegate) {
            super(delegate.getParams());
            this.delegate = delegate;
            this.prefixFirst = false;
            this.sqlBuffer = new StringBuilder();
        }

        public void applyAll() {
            sqlBuffer = new StringBuilder(sqlBuffer.toString().trim());
            String uppercaseSqlToTrim = sqlBuffer.toString().toUpperCase(Locale.ENGLISH);
            if (uppercaseSqlToTrim.length() > 0) {
                applyPrefix(sqlBuffer, uppercaseSqlToTrim);
            }
            delegate.appendSql(sqlBuffer.toString());
        }


        @Override
        public void appendSql(String sql) {
            sqlBuffer.append(sql);
        }

        @Override
        public String getSql() {
            return delegate.getSql();
        }

        private void applyPrefix(StringBuilder sql, String uppercaseSqlToTrim) {
            if (!prefixFirst) {
                //进入第一个语句以后设置为true
                prefixFirst = true;
                //遍历删除所有前缀
                for (String toRemove : prefixesToOverride) {
                    if (uppercaseSqlToTrim.startsWith(toRemove)) {
                        sql.delete(0, toRemove.trim().length());
                        break;
                    }
                }
                //开始位置插入前缀
                sql.insert(0, " ");
                sql.insert(0, "where");
            }
        }
    }
}