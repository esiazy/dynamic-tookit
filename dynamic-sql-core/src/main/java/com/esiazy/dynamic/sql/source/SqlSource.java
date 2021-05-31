package com.esiazy.dynamic.sql.source;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

/**
 * @author wxf
 * @date 2021/5/25 16:27
 */
public interface SqlSource {

    /**
     * getSql
     *
     * @param param param
     * @return BoundSql
     */
    BoundSql getBoundSql(MetaHashMap param);

}
