package com.buildupchao.zns.server.push;

import com.buildupchao.zns.api.annotation.ZnsService;
import com.buildupchao.zns.common.util.IpUtil;
import com.buildupchao.zns.server.config.ZnsServerConfiguration;
import com.buildupchao.zns.server.util.SpringBeanFactory;
import com.buildupchao.zns.server.util.ZKit;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * Service push manager:
 * Provide a service that register service info into zookeeper.
 *
 * @author buildupchao
 *         Date: 2019/1/31 23:49
 * @since JDK 1.8
 */
@Component
public class ServicePushManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicePushManager.class);

    @Autowired
    private ZKit zKit;

    @Autowired
    private ZnsServerConfiguration configuration;

    public void registerIntoZK() {
        Map<String, Object> beanWithAnnotations =
                SpringBeanFactory.getBeanListByAnnotationClass(ZnsService.class);
        if (MapUtils.isEmpty(beanWithAnnotations)) {
            return;
        }

        zKit.createRootNode();
        for (Object bean : beanWithAnnotations.values()) {
            ZnsService znsService = bean.getClass().getAnnotation(ZnsService.class);
            String serviceName = znsService.cls().getName();
            pushServiceInfoIntoZK(serviceName);
        }
        LOGGER.info("Register service into zookeeper successfully");
    }

    private void pushServiceInfoIntoZK(String serviceName) {
        // Create persistent service node
        zKit.createPersistentNode(serviceName);

        String serviceAddress = IpUtil.getRealIp()
                + ":" + configuration.getServerPort()
                + ":" + configuration.getNetworkPort();
        String serviceAddressPath = serviceName + "/" + serviceAddress;
        zKit.createNode(serviceAddressPath);

        LOGGER.info("Register service[{}] into zookeeper successfully", serviceAddressPath);
    }
}
