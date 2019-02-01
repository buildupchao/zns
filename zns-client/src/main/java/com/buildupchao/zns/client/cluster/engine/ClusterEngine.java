package com.buildupchao.zns.client.cluster.engine;

import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.buildupchao.zns.client.cluster.ClusterStrategyEnum;
import com.buildupchao.zns.client.cluster.impl.RandomClusterStrategyImpl;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author buildupchao
 *         Date: 2019/2/1 02:45
 * @since JDK 1.8
 */
public class ClusterEngine {

    private static final Map<ClusterStrategyEnum, ClusterStrategy> clusterStrategyMap = Maps.newConcurrentMap();

    static {
        clusterStrategyMap.put(ClusterStrategyEnum.RANDOM, new RandomClusterStrategyImpl());
    }

    public static ClusterStrategy queryClusterStrategy(String clusterStrategy) {
        ClusterStrategyEnum clusterStrategyEnum = ClusterStrategyEnum.queryByCode(clusterStrategy);
        if (clusterStrategyEnum == null) {
            return new RandomClusterStrategyImpl();
        }
        return clusterStrategyMap.get(clusterStrategyEnum);
    }
}
