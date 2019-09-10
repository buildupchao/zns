package com.buildupchao.zns.client.cluster.engine;

import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.buildupchao.zns.client.cluster.ClusterStrategyEnum;
import com.buildupchao.zns.client.cluster.impl.*;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author buildupchao
 * @date 2019/2/1 02:45
 * @since JDK 1.8
 */
public class ClusterEngine {

    private static final Map<ClusterStrategyEnum, ClusterStrategy> clusterStrategyMap = Maps.newConcurrentMap();

    static {
        clusterStrategyMap.put(ClusterStrategyEnum.RANDOM, new RandomClusterStrategyImpl());
        clusterStrategyMap.put(ClusterStrategyEnum.WEIGHT_RANDOM, new WeightRandomClusterStrategyImpl());
        clusterStrategyMap.put(ClusterStrategyEnum.POLLING, new PollingClusterStrategyImpl());
        clusterStrategyMap.put(ClusterStrategyEnum.WEIGHT_POLLING, new WeightPollingClusterStrategyImpl());
        clusterStrategyMap.put(ClusterStrategyEnum.HASH, new HashClusterStrategyImpl());
    }

    public static ClusterStrategy queryClusterStrategy(String clusterStrategy) {
        ClusterStrategyEnum clusterStrategyEnum = ClusterStrategyEnum.queryByCode(clusterStrategy);
        if (clusterStrategyEnum == null) {
            return new RandomClusterStrategyImpl();
        }
        return clusterStrategyMap.get(clusterStrategyEnum);
    }
}
