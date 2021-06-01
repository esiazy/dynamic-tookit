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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态接口执行服务
 *
 * @author wxf
 * @date 2021/4/15 20:24
 */
@Slf4j
public class DynamicInterfaceExecutor {
    private static final Map<String, DynamicInterfaceCache> OBJECT_MAP = new HashMap<>(64);

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
            throw new NoneMethodException(wrapper.getMethod() + " is not found in object named [" + controller + "]");
        }

        Method method = methodCache.getMethod();
        List<Object> paramList = compileService.parseParameterTypes(wrapper, methodCache.getMethodParamTypes());

        if (wrapper.isPrivate()) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        }

        return this.invoker.invoker(processor, method, paramList.toArray(new Object[]{}));
    }

    private DynamicInterfaceCache getAndCheckUpdate(String controller) throws CompileFailErrorException {
        DynamicConfigEntity entity = entityConfig.getOne(controller);
        DynamicInterfaceCache interfaceCache = getWhenNullBuild(entity);
        if (interfaceCache == null || entity.getUpdateTime().getTime() != interfaceCache.getLastUpdateTime().getTime()) {
            log.info("cache obj size :{} compare object {}", OBJECT_MAP.size(), controller);
            interfaceCache = buildObj(entity);
            OBJECT_MAP.put(controller, interfaceCache);
        }
        return interfaceCache;
    }

    private DynamicInterfaceCache getWhenNullBuild(DynamicConfigEntity entity) throws CompileFailErrorException {
        DynamicInterfaceCache interfaceCache = OBJECT_MAP.get(entity.getController());
        if (interfaceCache == null) {
            synchronized (DynamicInterfaceExecutor.class) {
                interfaceCache = OBJECT_MAP.get(entity.getController());
                if (interfaceCache != null) {
                    return interfaceCache;
                }
                interfaceCache = buildObj(entity);
            }
        }
        OBJECT_MAP.put(entity.getController(), interfaceCache);
        return interfaceCache;
    }

    private synchronized DynamicInterfaceCache buildObj(DynamicConfigEntity entity) throws CompileFailErrorException {
        DynamicInterfaceCache interfaceCache;
        interfaceCache = new DynamicInterfaceCache();
        long start = System.currentTimeMillis();
        Object executeProcess = compileService.getInstance(entity.getCode());
        log.info("instance object success {} cast time :{}ms", entity.getController(), System.currentTimeMillis() - start);
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
        return interfaceCache;
    }
}
