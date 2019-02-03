package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author buildupchao
 *         Date: 2019/2/4 07:24
 * @since JDK 1.8
 */
public class PollingClusterStrategyImpl implements ClusterStrategy {

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        int size = serviceRoutes.size();
        long position = counter.incrementAndGet() % size;
        if (position < 0) {
            position = 0;
        }
        return serviceRoutes.get((int) position);
    }
}
