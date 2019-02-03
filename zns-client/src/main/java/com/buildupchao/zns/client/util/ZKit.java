package com.buildupchao.zns.client.util;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cache.ServiceRouteCache;
import com.buildupchao.zns.client.config.ZnsClientConfiguration;
import com.google.common.collect.Lists;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author buildupchao
 *         Date: 2019/2/1 01:24
 * @since JDK 1.8
 */
@Component
public class ZKit {

    @Autowired
    private ZnsClientConfiguration configuration;

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ServiceRouteCache serviceRouteCache;

    public void subscribeZKEvent(String serviceName) {
        String path = configuration.getZkRoot() + "/" + serviceName;
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                if (CollectionUtils.isNotEmpty(list)) {
                    List<ProviderService> providerServices = convertToProviderService(list);
                    serviceRouteCache.updateCache(serviceName, providerServices);
                }
            }
        });
    }

    public List<ProviderService> getServiceInfos(String serviceName) {
        String path = configuration.getZkRoot() + "/" + serviceName;
        List<String> children = zkClient.getChildren(path);

        List<ProviderService> providerServices = convertToProviderService(children);
        return providerServices;
    }

    private List<ProviderService> convertToProviderService(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<ProviderService> providerServices = list.stream().map(v -> {
            String[] serviceInfos = v.split(":");
            return ProviderService.builder()
                    .serverIp(serviceInfos[0])
                    .serverPort(Integer.parseInt(serviceInfos[1]))
                    .networkPort(Integer.parseInt(serviceInfos[2]))
                    .build();
        }).collect(Collectors.toList());
        return providerServices;
    }
}
