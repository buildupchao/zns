package com.buildupchao.zns.server.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/1/31 22:06
 * @since JDK 1.8
 */
public interface ClassScanner {

    List<Class<?>> getClassListByAnnotationClass(String packageName, Class<? extends Annotation> annotationClass);
}
