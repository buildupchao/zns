package com.buildupchao.zns.client.cluster.impl;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.buildupchao.zns.common.util.IpUtil;

import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/2/4 22:45
 * @since JDK 1.8
 */
public class HashClusterStrategyImpl implements ClusterStrategy {

    @Override
    public ProviderService select(List<ProviderService> serviceRoutes) {
        String realIp = IpUtil.getRealIp();
        int hashCode = realIp.hashCode();

        int size = serviceRoutes.size();
        return serviceRoutes.get(hashCode % size);
    }
}
