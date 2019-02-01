package com.buildupchao.zns.server.core.impl;

import com.buildupchao.zns.server.core.ClassScanner;
import com.buildupchao.zns.server.core.impl.supports.AnnotationClassScannerSupport;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/1/31 22:11
 * @since JDK 1.8
 */
public class DefaultClassScanner implements ClassScanner {

    @Override
    public List<Class<?>> getClassListByAnnotationClass(String packageName, Class<? extends Annotation>
            annotationClass) {
        return new AnnotationClassScannerSupport(packageName, annotationClass) {
            @Override
            protected boolean checkIfAddClass(Class<?> cls) {
                return cls.isAnnotationPresent(annotationClass);
            }
        }.getClassList();
    }
}
