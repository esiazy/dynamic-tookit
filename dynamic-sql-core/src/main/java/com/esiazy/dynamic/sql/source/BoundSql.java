package com.esiazy.dynamic.sql.source;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import java.util.List;

/**
 * @author wxf
 * @date 2021/5/25 9:59
 */
public final class BoundSql {
    private final List<String> bindsParamName;

    private final String sql;

    private final MetaHashMap param;

    public BoundSql(List<String> bindsParamName, String sql, MetaHashMap param) {
        this.bindsParamName = bindsParamName;
        this.sql = sql;
        this.param = param;
    }

    public List<String> getBindsParamName() {
        return bindsParamName;
    }

    public String getSql() {
        return sql;
    }

    public MetaHashMap getParam() {
        return param;
    }
}
