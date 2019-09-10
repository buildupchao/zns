package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author buildupchao
 * @date 2019/2/4 07:24
 * @since JDK 1.8
 */
public class PollingClusterStrategyImpl implements ClusterStrategy {

    private int counter = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        ProviderService providerService = null;
        try {
            lock.tryLock(10, TimeUnit.SECONDS);
            int size = serviceRoutes.size();
            if (counter >= size) {
                counter = 0;
            }

            providerService = serviceRoutes.get(counter);
            counter++;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

        if (providerService == null) {
            providerService = serviceRoutes.get(0);
        }
        return providerService;
    }
}
