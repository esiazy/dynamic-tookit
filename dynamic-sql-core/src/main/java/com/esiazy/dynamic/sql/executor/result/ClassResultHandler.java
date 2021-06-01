package com.esiazy.dynamic.sql.executor.result;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.core.type.ClassTypeCache;
import com.esiazy.dynamic.core.type.TypeReference;
import com.esiazy.dynamic.core.util.TypeUtil;
import com.esiazy.dynamic.sql.annotation.DynamicField;
import com.esiazy.dynamic.sql.exceptions.ExecutorException;
import com.esiazy.dynamic.sql.exceptions.SqlRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author wxf
 * @date 2021/6/1 4:21 下午
 */
public class ClassResultHandler extends BaseResultHandler {
    private static final Map<Class<?>, Handler> HANDLER_MAP = new HashMap<>(32);

    static {
        Handler stringHandler = Object::toString;
        Handler intHandler = TypeUtil::castToInt;
        Handler booleanHandler = TypeUtil::castToBoolean;
        Handler dateHandler = TypeUtil::castToDate;
        Handler charHandler = TypeUtil::castToChar;
        Handler doubleHandler = TypeUtil::castToDouble;
        Handler decimalHandler = TypeUtil::castToBigDecimal;
        Handler integerHandler = TypeUtil::castToBigInteger;
        Handler shortHandler = TypeUtil::castToShort;
        Handler longHandler = TypeUtil::castToLong;
        Handler floatHandler = TypeUtil::castToFloat;
        HANDLER_MAP.put(int.class, intHandler);
        HANDLER_MAP.put(Integer.class, intHandler);
        HANDLER_MAP.put(long.class, longHandler);
        HANDLER_MAP.put(Long.class, longHandler);
        HANDLER_MAP.put(double.class, doubleHandler);
        HANDLER_MAP.put(Double.class, doubleHandler);
        HANDLER_MAP.put(char.class, charHandler);
        HANDLER_MAP.put(Character.class, charHandler);
        HANDLER_MAP.put(float.class, floatHandler);
        HANDLER_MAP.put(Float.class, floatHandler);
        HANDLER_MAP.put(boolean.class, booleanHandler);
        HANDLER_MAP.put(Boolean.class, booleanHandler);
        HANDLER_MAP.put(short.class, shortHandler);
        HANDLER_MAP.put(Short.class, shortHandler);
        HANDLER_MAP.put(BigDecimal.class, decimalHandler);
        HANDLER_MAP.put(BigInteger.class, integerHandler);
        HANDLER_MAP.put(String.class, stringHandler);
        HANDLER_MAP.put(java.sql.Date.class, dateHandler);
    }

    private final TypeReference<?> reference;
    private final List<ClassTypeCache> entityCache;

    public ClassResultHandler(TypeReference<?> reference) {
        this.reference = reference;
        this.entityCache = cacheEntityFieldTypes(reference.getClazz());
    }

    @Override
    protected List<Object> handler(ResultSet resultSet) throws SQLException {
        List<Object> metaMapList = new ArrayList<>(32);
        while (resultSet.next()) {
            Object instance;
            try {
                instance = reference.getClazz().newInstance();
            } catch (Exception e) {
                throw new ExecutorException("result handler instance object error:", e);
            }
            ResultSetMetaData resumed = resultSet.getMetaData();
            int cols = resumed.getColumnCount();
            MetaHashMap rowMap = new MetaHashMap();
            for (int i = 1; i <= cols; i++) {
                String columnLabel = resumed.getColumnLabel(i);
                Object value = resultSet.getObject(i);
                rowMap.put(columnLabel, value);
            }
            try {
                setEntityValue(instance, rowMap);
            } catch (IllegalAccessException e) {
                throw new SqlRuntimeException("result handler setEntityValue error:", e);
            }
            metaMapList.add(instance);
        }
        return metaMapList;
    }

    private void setEntityValue(Object obj, MetaHashMap metaHashMap) throws IllegalAccessException {
        for (ClassTypeCache typeCache : entityCache) {
            Field field = typeCache.getField();
            DynamicField fieldAnnotation = field.getAnnotation(DynamicField.class);
            Object value = metaHashMap.get(fieldAnnotation == null ||
                    StringUtils.isBlank(fieldAnnotation.value())
                    ? camelToUnderline(typeCache.getName()) : fieldAnnotation.value());
            value = value == null ? metaHashMap.get(camelToUnderline(typeCache.getName()).toUpperCase(Locale.ROOT)) : value;
            if (value != null) {
                Class<?> type = typeCache.getType();
                Handler handler = HANDLER_MAP.get(type);
                value = handler == null ? value : handler.handler(value);
                field.set(obj, value);
            }
        }
    }

    public String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    private List<ClassTypeCache> cacheEntityFieldTypes(Class<?> clazz) {
        List<ClassTypeCache> caches = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            caches.add(new ClassTypeCache(field, field.getType(), field.getName(), field.isAccessible()));
        }
        return caches;
    }

    private interface Handler {
        Object handler(Object obj);
    }
}
