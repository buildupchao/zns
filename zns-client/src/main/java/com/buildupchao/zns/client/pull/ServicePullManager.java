package com.buildupchao.zns.client.pull;

import com.buildupchao.zns.api.annotation.ZnsClient;
import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cache.ServiceRouteCache;
import com.buildupchao.zns.client.config.ZnsClientConfiguration;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import com.buildupchao.zns.client.util.ZKit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service pull manager:
 * Pull service provider list from zookeeper
 *
 * @author buildupchao
 *         Date: 2019/2/1 02:32
 * @since JDK 1.8
 */
@Component
public class ServicePullManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePullManager.class);

    @Autowired
    private ZKit zKit;

    @Autowired
    private ServiceRouteCache serviceRouteCache;

    @Autowired
    private ZnsClientConfiguration configuration;

    public void pullServiceFromZK() {
        Reflections reflections = new Reflections(configuration.getZnsClientApiPackage());
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(ZnsClient.class);
        if (CollectionUtils.isEmpty(typesAnnotatedWith)) {
            return;
        }
        for (Class<?> cls : typesAnnotatedWith) {
            String serviceName = cls.getName();

            // Cache service provider list into local
            List<ProviderService> providerServices = zKit.getServiceInfos(serviceName);
            serviceRouteCache.addCache(serviceName, providerServices);

            // Add listener for service node
            zKit.subscribeZKEvent(serviceName);
        }

        LOGGER.info("Pull service address list from zookeeper successfully");
    }
}
