package com.buildupchao.zns.server.core.impl.supports;

import java.lang.annotation.Annotation;

/**
 * @author buildupchao
 *         Date: 2019/1/31 23:27
 * @since JDK 1.8
 */
public abstract class AnnotationClassScannerSupport extends ClassScannerSupport {

    protected final Class<? extends Annotation> annotationClass;

    public AnnotationClassScannerSupport(String packageName, Class<? extends Annotation> annotationClass) {
        super(packageName);
        this.annotationClass = annotationClass;
    }
}
