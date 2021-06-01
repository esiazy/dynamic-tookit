package com.esiazy.dynamic.sql.source;

import java.util.Map;

/**
 * @author wxf
 * @date 2021/5/25 16:27
 */
public interface SqlSource {

    /**
     * getSql
     *
     * @param parameter param
     * @return BoundSql
     */
    BoundSql getBoundSql(Map<String, Object> parameter);

}
