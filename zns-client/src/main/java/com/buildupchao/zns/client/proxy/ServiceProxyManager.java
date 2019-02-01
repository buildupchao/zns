package com.buildupchao.zns.client.proxy;

import com.buildupchao.zns.api.annotation.ZnsClient;
import com.buildupchao.zns.client.config.ZnsClientConfiguration;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author buildupchao
 *         Date: 2019/2/1 14:14
 * @since JDK 1.8
 */
@Component
public class ServiceProxyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProxyManager.class);

    @Autowired
    private ZnsClientConfiguration configuration;

    @Autowired
    private ProxyHelper proxyHelper;

    public void initServiceProxyInstance() {
        Reflections reflections = new Reflections(configuration.getZnsClientApiPackage());
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(ZnsClient.class);
        if (CollectionUtils.isEmpty(typesAnnotatedWith)) {
            return;
        }

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) SpringBeanFactory.context()
                .getAutowireCapableBeanFactory();
        for (Class<?> cls : typesAnnotatedWith) {
            ZnsClient znsClient = cls.getAnnotation(ZnsClient.class);
            String serviceName = cls.getName();
            beanFactory.registerSingleton(serviceName, proxyHelper.newProxyInstance(cls));
        }

        LOGGER.info("Initialize proxy for service successfully");
    }
}
