package com.esiazy.dynamic.code;

import com.esiazy.dynamic.code.exception.runtime.NoneTargetException;
import com.esiazy.dynamic.core.entity.DynamicConfigEntity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wxf
 * @date 2021/5/29 11:08
 */
public interface EntityConfig {
    AtomicInteger INTEGER = new AtomicInteger(0);

    DynamicConfigEntity getOne(String controller) throws NoneTargetException;
}
