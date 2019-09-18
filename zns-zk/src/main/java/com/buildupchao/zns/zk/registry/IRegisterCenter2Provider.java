package com.buildupchao.zns.zk.registry;

import com.buildupchao.zns.zk.bean.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @author buildupchao
 * @date 2019/2/17 17:13
 * @since JDK 1.8
 */
public interface IRegisterCenter2Provider {

    void registerProvider(final List<ProviderService> providerServices);

    Map<String, List<ProviderService>> getProviderServiceMap();
}
