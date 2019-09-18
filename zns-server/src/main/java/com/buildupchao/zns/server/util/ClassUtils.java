package com.buildupchao.zns.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 * @date 2019/1/31 22:14
 * @since JDK 1.8
 */
public class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Load class {} error!", className, e);
            throw new RuntimeException(e);
        }
        return cls;
    }
}
