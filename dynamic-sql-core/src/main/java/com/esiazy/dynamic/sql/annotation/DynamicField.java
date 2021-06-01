package com.esiazy.dynamic.sql.annotation;

import java.lang.annotation.*;

/**
 * @author wxf
 * @date 2021/6/1 5:11 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicField {
    String value() default "";

    String dateFormat() default "";
}
