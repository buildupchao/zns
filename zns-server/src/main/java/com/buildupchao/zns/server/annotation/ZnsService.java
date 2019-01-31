package com.buildupchao.zns.server.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface to flag a class as being handled through the zns rpc.
 *
 * @author buildupchao
 *         Date: 2019/1/31 23:39
 * @since JDK 1.8
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZnsService {
    String version() default "";
}
