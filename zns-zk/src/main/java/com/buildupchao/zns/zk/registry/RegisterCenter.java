package com.buildupchao.zns.zk.registry;

import com.buildupchao.zns.zk.bean.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buildupchao
 * @date 2019/2/17 17:32
 * @since JDK 1.8
 */
public class RegisterCenter implements IRegisterCenter2Provider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterCenter.class);

    private static RegisterCenter registerCenter = new RegisterCenter();

    private static final String PROVIDER_TYPE = "provider";
    private static final String CONSUMER_TYPE = "consumer";
    private static final ConcurrentHashMap<String, List<ProviderService>> providerServiceMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<ProviderService>> serviceMetaDataMap4Consumer = new ConcurrentHashMap<>();

    private RegisterCenter() {}

    public void initRegisterCenter() {

    }

    @Override
    public void registerProvider(List<ProviderService> providerServices) {

    }

    @Override
    public Map<String, List<ProviderService>> getProviderServiceMap() {
        return null;
    }
}
