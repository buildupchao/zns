package com.buildupchao.zns.server.core;

import com.buildupchao.zns.server.core.impl.DefaultClassScanner;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/1/31 23:24
 * @since JDK 1.8
 */
public class ClassHelper {

    private static final ClassScanner classScanner = new DefaultClassScanner();

    public static List<Class<?>> getClassListByAnnotationClass(String packageName, Class<? extends Annotation> annotationClass) {
        return classScanner.getClassListByAnnotationClass(packageName, annotationClass);
    }
}
