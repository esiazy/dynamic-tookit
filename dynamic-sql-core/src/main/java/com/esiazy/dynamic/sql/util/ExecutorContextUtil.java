package com.esiazy.dynamic.sql.util;

import com.esiazy.dynamic.sql.cache.CacheKey;
import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.source.SqlSource;

import javax.sql.DataSource;
import java.util.Date;

/**
 * @author Esadmin
 * @date 2021/5/26 14:00
 */
public final class ExecutorContextUtil {
    private ExecutorContextUtil() {

    }

    /**
     * 生成缓存key
     * <p>{@link CacheKey#toString()} key生成规则参考toString方法</p>
     *
     * @param module 分组名称
     * @param target 目标名称
     * @return key
     * @see CacheKey
     */
    public static String buildCacheKey(String module, String target) {
        CacheKey key = new CacheKey();
        key.add(module, target);
        return key.toString();
    }

    public static String getModule(String id) {
        return id.split(":")[0];
    }

    public static String getTarget(String id) {
        return id.split(":")[1];
    }

    public static ExecuteContext buildExecuteContext(SqlSource sqlSource, DataSource dataSource, long updateTime) {
        ExecuteContext context = new ExecuteContext();
        context.setDataSource(dataSource);
        context.setSqlSource(sqlSource);
        context.setLastUpdateTime(new Date(updateTime));
        return context;
    }
}
