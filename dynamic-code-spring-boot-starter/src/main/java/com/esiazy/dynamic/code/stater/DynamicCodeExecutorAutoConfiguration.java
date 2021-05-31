package com.esiazy.dynamic.code.stater;


import com.esiazy.dynamic.code.DynamicInterfaceExecutor;
import com.esiazy.dynamic.code.EntityConfig;
import com.esiazy.dynamic.code.compile.CompileService;
import com.esiazy.dynamic.code.compile.DefaultGroovyCompile;
import com.esiazy.dynamic.code.invoker.DynamicInvoker;
import com.esiazy.dynamic.code.invoker.Invoker;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author wxf
 */
@org.springframework.context.annotation.Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@AutoConfigureOrder(257)
public class DynamicCodeExecutorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = {CompileService.class, Invoker.class})
    public DynamicInterfaceExecutor defaultExecutorBean(EntityConfig entityConfig) {
        return new DynamicInterfaceExecutor(entityConfig);
    }

    @Bean
    @ConditionalOnBean(value = {CompileService.class, Invoker.class})
    public DynamicInterfaceExecutor customerExecutorBean(EntityConfig entityConfig, CompileService compileService, Invoker invoker) {
        return new DynamicInterfaceExecutor(compileService, entityConfig, invoker);
    }

    @Bean
    @ConditionalOnBean(value = Invoker.class)
    @ConditionalOnMissingBean(CompileService.class)
    public DynamicInterfaceExecutor compareExecutorBean(EntityConfig entityConfig, Invoker invoker) {
        return new DynamicInterfaceExecutor(new DefaultGroovyCompile(), entityConfig, invoker);
    }

    @Bean
    @ConditionalOnBean(value = CompileService.class)
    @ConditionalOnMissingBean(Invoker.class)
    public DynamicInterfaceExecutor invokerExecutorBean(EntityConfig entityConfig, CompileService compileService) {
        return new DynamicInterfaceExecutor(compileService, entityConfig, new DynamicInvoker());
    }
}