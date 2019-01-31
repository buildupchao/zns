package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/2/1 02:50
 * @since JDK 1.8
 */
public class RandomClusterStrategyImpl implements ClusterStrategy {

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        int MAX_LEN = serviceRoutes.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return serviceRoutes.get(index);
    }
}
