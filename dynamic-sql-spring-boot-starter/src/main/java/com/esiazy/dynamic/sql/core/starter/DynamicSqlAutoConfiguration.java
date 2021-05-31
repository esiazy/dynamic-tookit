package com.esiazy.dynamic.sql.core.starter;

import com.esiazy.dynamic.sql.cache.ContextCache;
import com.esiazy.dynamic.sql.cache.DynamicCacheConfig;
import com.esiazy.dynamic.sql.executor.Executor;
import com.esiazy.dynamic.sql.plugin.Interceptor;
import com.esiazy.dynamic.sql.session.MapSqlSession;
import com.esiazy.dynamic.sql.source.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author wxf
 */
@org.springframework.context.annotation.Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@AutoConfigureOrder(256)
public class DynamicSqlAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Configuration configurationBean(DataSource dataSource,
                                           @Autowired(required = false) InterceptorConfig interceptorConfig) {
        Configuration configuration = new Configuration(dataSource);
        if (interceptorConfig != null) {
            for (Interceptor interceptor : interceptorConfig.getInterceptors()) {
                configuration.addInterceptor(interceptor);
            }
        }
        return configuration;
    }

    @Bean
    @ConditionalOnMissingBean
    public Executor baseExecutorBean(Configuration configuration) {
        return configuration.newExecutor();
    }

    @Bean
    @ConditionalOnMissingBean
    public MapSqlSession mapSqlSessionBean(Configuration configuration, Executor executor, ContextCache contextCache) {
        CacheInterceptor interceptor = new CacheInterceptor(contextCache);
        configuration.addInterceptor(interceptor);
        return configuration.newSqlSession(executor);
    }

    @Bean
    @ConditionalOnMissingBean
    public ContextCache dynamicCacheConfigBean(Configuration configuration, DynamicCacheConfig.Query query) {
        return configuration.newCacheConfig(query);
    }
}