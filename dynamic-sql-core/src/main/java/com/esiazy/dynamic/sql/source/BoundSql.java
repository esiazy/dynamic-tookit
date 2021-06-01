package com.esiazy.dynamic.sql.source;

import java.util.List;
import java.util.Map;

/**
 * @author wxf
 * @date 2021/5/25 9:59
 */
public final class BoundSql {
    private final List<String> bindsParamName;

    private final String sql;

    private final Map<String, Object> param;

    public BoundSql(List<String> bindsParamName, String sql, Map<String, Object> param) {
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

    public Map<String, Object> getParam() {
        return param;
    }
}
