package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author buildupchao
 * @date 2019/2/4 22:37
 * @since JDK 1.8
 */
public class WeightRandomClusterStrategyImpl implements ClusterStrategy {

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        List<ProviderService> providerServices = Lists.newArrayList();
        for (ProviderService providerService : serviceRoutes) {
            int weight = providerService.getWeight();
            for (int i = 0; i < weight; i++) {
                providerServices.add(providerService);
            }
        }

        int MAX_LEN = providerServices.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerServices.get(index);
    }
}
