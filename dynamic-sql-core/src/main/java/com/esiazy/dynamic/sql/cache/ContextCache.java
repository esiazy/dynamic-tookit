package com.esiazy.dynamic.sql.cache;

import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.util.ExecutorContextUtil;

/**
 * 上下文缓存
 * <p>生成id参考@see</p>
 *
 * @author wxf
 * @date 2021/5/26 11:29
 * @see ExecutorContextUtil#buildCacheKey(String, String)
 */
public interface ContextCache {
    /**
     * 检查目标是否存在
     * 如不存在则进行查询并更新
     * <p>默认懒加载</p>
     *
     * @param contextId 上下文缓存ID
     */
    void checkAndUpdate(String contextId);

    /**
     * 获取一个目标对象
     *
     * @param contextId 上下文缓存ID
     * @return sql执行器上下文
     */
    ExecuteContext getOne(String contextId);

    /**
     * 获取目标更新时间
     *
     * @param contextId 上下文缓存ID
     * @return 上次更新时间
     */
    long getUpdateTime(String contextId);
}
