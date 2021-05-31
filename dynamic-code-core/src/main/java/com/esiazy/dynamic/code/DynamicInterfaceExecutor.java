package com.esiazy.dynamic.code;

import com.esiazy.dynamic.code.annotation.DgMethod;
import com.esiazy.dynamic.code.compile.CompileService;
import com.esiazy.dynamic.code.compile.DefaultGroovyCompile;
import com.esiazy.dynamic.code.entity.DynamicInterfaceCache;
import com.esiazy.dynamic.code.entity.DynamicInterfaceMethodCache;
import com.esiazy.dynamic.code.exception.CompileFailErrorException;
import com.esiazy.dynamic.code.exception.ExecutorException;
import com.esiazy.dynamic.code.exception.runtime.NoneMethodException;
import com.esiazy.dynamic.code.exception.runtime.NoneTargetException;
import com.esiazy.dynamic.code.invoker.DynamicInvoker;
import com.esiazy.dynamic.code.invoker.Invoker;
import com.esiazy.dynamic.core.entity.DynamicConfigEntity;
import com.esiazy.dynamic.core.entity.DynamicWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态接口执行服务
 *
 * @author wxf
 * @date 2021/4/15 20:24
 */
@Slf4j
public class DynamicInterfaceExecutor {
    private static final Map<String, DynamicInterfaceCache> OBJECT_MAP = new ConcurrentHashMap<>(64);

    private final CompileService compileService;

    private final EntityConfig entityConfig;

    private final Invoker invoker;

    public DynamicInterfaceExecutor(EntityConfig entityConfig) {
        this(new DefaultGroovyCompile(), entityConfig, new DynamicInvoker());
    }

    public DynamicInterfaceExecutor(CompileService compileService, EntityConfig entityConfig, Invoker invoker) {
        this.compileService = compileService;
        this.entityConfig = entityConfig;
        this.invoker = invoker;
    }

    public Object executeProcessor(DynamicWrapper wrapper) throws CompileFailErrorException, ExecutorException {
        String controller = wrapper.getController();

        DynamicInterfaceCache processorCache = getAndCheckUpdate(controller);

        Object processor = processorCache.getTarget();
        if (processor == null) {
            throw new NoneTargetException(controller + " not found please check your config");
        }

        DynamicInterfaceMethodCache methodCache = processorCache.getMethodCache().get(wrapper.getMethod());
        if (methodCache == null) {
            throw new NoneMethodException(wrapper.getMethod() + "is not found in object named [" + controller + "]");
        }
        Method method = methodCache.getMethod();
        Class<?>[] paramTypes = methodCache.getMethodParamTypes();
        List<Object> paramList = compileService.parseParameterTypes(wrapper, paramTypes);

        if (wrapper.isPrivate()) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        }

        return this.invoker.invoker(processor, method, paramList.toArray(new Object[]{}));
    }

    private DynamicInterfaceCache getAndCheckUpdate(String controller) throws CompileFailErrorException {
        DynamicInterfaceCache interfaceCache = OBJECT_MAP.get(controller);
        DynamicConfigEntity entity = entityConfig.getOne(controller);
        if (interfaceCache == null || entity.getUpdateTime().getTime() != interfaceCache.getLastUpdateTime().getTime()) {
            Long time = null;
            if (interfaceCache != null) {
                time = interfaceCache.getLastUpdateTime().getTime();
            }
            log.info("compare object " + controller + " cache time:" + time + " entity time:" + entity.getUpdateTime().getTime());
            interfaceCache = buildObject(entity);
        }
        return interfaceCache;
    }

    private DynamicInterfaceCache buildObject(DynamicConfigEntity entity) throws CompileFailErrorException {
        DynamicInterfaceCache interfaceCache = OBJECT_MAP.get(entity.getController());
        if (interfaceCache == null) {
            synchronized (DynamicInterfaceExecutor.class) {
                interfaceCache = OBJECT_MAP.get(entity.getController());
                if (interfaceCache != null) {
                    return interfaceCache;
                }
                interfaceCache = new DynamicInterfaceCache();
                Object executeProcess = compileService.getInstance(entity.getCode());
                //对method做缓存
                List<Method> declaredMethods = new ArrayList<>();
                Class<?> aclazz = executeProcess.getClass();
                while (aclazz != null) {
                    Method[] declaredMethodsChild = aclazz.getDeclaredMethods();
                    Collections.addAll(declaredMethods, declaredMethodsChild);
                    aclazz = aclazz.getSuperclass();
                }
                Map<String, DynamicInterfaceMethodCache> methodCacheMap = new ConcurrentHashMap<>(16);
                for (Method m : declaredMethods) {
                    DgMethod dgMethod = AnnotationUtils.getAnnotation(m, DgMethod.class);
                    if (dgMethod != null && !StringUtils.isEmpty(dgMethod.value())) {
                        methodCacheMap.put(dgMethod.value(), new DynamicInterfaceMethodCache(m, m.getParameterTypes()));
                        methodCacheMap.put(m.getName(), new DynamicInterfaceMethodCache(m, m.getParameterTypes()));
                    }
                }
                interfaceCache.setMethodCache(methodCacheMap);
                interfaceCache.setLastUpdateTime(entity.getUpdateTime());
                interfaceCache.setTarget(executeProcess);
            }
        }
        OBJECT_MAP.put(entity.getController(), interfaceCache);
        return interfaceCache;
    }
}
