package com.buildupchao.zns.server.push;

import com.buildupchao.zns.common.util.IpUtil;
import com.buildupchao.zns.server.annotation.ZnsService;
import com.buildupchao.zns.server.config.ZnsServerConfiguration;
import com.buildupchao.zns.server.util.SpringBeanFactory;
import com.buildupchao.zns.server.util.ZKit;
import org.apache.commons.collections.MapUtils;
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

    @Autowired
    private ZKit zKit;

    @Autowired
    private ZnsServerConfiguration configuration;

    public void registerIntoZK() {
        Map<String, Object> beanWithAnnotations =
                SpringBeanFactory.getClassListByAnnotationClass(ZnsService.class);
        if (MapUtils.isEmpty(beanWithAnnotations)) {
            return;
        }

        zKit.createRootNode();
        for (Map.Entry<String, Object> beanEntry : beanWithAnnotations.entrySet()) {
            String serviceName = beanEntry.getValue().getClass().getName();
            pushServiceInfoIntoZK(serviceName);
        }
    }

    private void pushServiceInfoIntoZK(String serviceName) {
        // 创建服务节点
        zKit.createNode(serviceName);

        String serviceAddress = IpUtil.getHostAddress() + ":" + configuration.getServerPort();
        String serviceAddressPath = serviceName + File.separator + serviceAddress;
        zKit.createNode(serviceAddressPath);
    }
}
