package com.buildupchao.zns.client.cluster;

import com.buildupchao.zns.client.bean.ProviderService;

import java.util.List;

/**
 * @author buildupchao
 * @date 2019/2/1 02:44
 * @since JDK 1.8
 */
public interface ClusterStrategy {

    ProviderService select(List<ProviderService> serviceRoutes);
}
