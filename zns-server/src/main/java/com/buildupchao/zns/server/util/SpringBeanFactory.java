package com.buildupchao.zns.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author buildupchao
 *         Date: 2019/1/31 21:52
 * @since JDK 1.8
 */
@Component
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> cls) {
        return context.getBean(cls);
    }

    public static <T> T getBean(String name, Class<T> cls) {
        return context.getBean(name, cls);
    }

    public static Map<String, Object> getClassListByAnnotationClass(Class<? extends Annotation> annotationClass) {
         return context.getBeansWithAnnotation(annotationClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
