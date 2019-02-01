package com.buildupchao.zns.client.cache;

import com.buildupchao.zns.api.annotation.ZnsClient;
import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import com.buildupchao.zns.client.util.ZKit;
import com.buildupchao.zns.common.exception.ZnsException;
import com.google.common.cache.LoadingCache;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author buildupchao
 *         Date: 2019/2/1 01:47
 * @since JDK 1.8
 */
@Component
public class ServiceRouteCache {

    @Autowired
    private LoadingCache<String, List<ProviderService>> cache;

    @Autowired
    private ZKit zKit;


    public void addCache(String serviceName, List<ProviderService> serviceRoutes) {
        cache.put(serviceName, serviceRoutes);
    }

    public void updateCache(String serviceName, List<ProviderService> serviceRoutes) {
        cache.put(serviceName, serviceRoutes);
    }

    public void updateCache(Map<String, List<ProviderService>> newServiceRoutesMap) {
        cache.invalidateAll();
        for (Map.Entry<String, List<ProviderService>> entry : newServiceRoutesMap.entrySet()) {
            cache.put(entry.getKey(), entry.getValue());
        }
    }

    public List<ProviderService> getServiceRoutes(String serviceName) {
        if (cache.size() == 0) {
            reloadCache();

            if (cache.size() == 0) {
                throw new ZnsException("Not any service which is available.");
            }
        }
        try {
            return cache.get(serviceName);
        } catch (ExecutionException e) {
            throw new ZnsException(e);
        }
    }

    private void reloadCache() {
        Map<String, Object> beans = SpringBeanFactory.getBeanListByAnnotationClass(ZnsClient.class);
        if (MapUtils.isEmpty(beans)) {
            return;
        }
        for (Object bean : beans.values()) {
            String serviceName = bean.getClass().getName();
            List<ProviderService> serviceRoutes = zKit.getServiceInfos(serviceName);
            addCache(serviceName, serviceRoutes);
        }
    }
}
