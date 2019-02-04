package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author buildupchao
 *         Date: 2019/2/4 22:39
 * @since JDK 1.8
 */
public class WeightPollingClusterStrategyImpl implements ClusterStrategy {

    private int counter = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        ProviderService providerService = null;

        try {
            lock.tryLock(10, TimeUnit.SECONDS);
            List<ProviderService> providerServices = Lists.newArrayList();
            for (ProviderService serviceRoute : serviceRoutes) {
                int weight = serviceRoute.getWeight();
                for (int i = 0; i < weight; i++) {
                    providerServices.add(serviceRoute);
                }
            }

            if (counter > providerServices.size()) {
                counter = 0;
            }
            providerService = providerServices.get(counter);
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
