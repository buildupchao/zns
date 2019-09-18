package com.buildupchao.zns.client.config;

import com.buildupchao.zns.client.bean.ProviderService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author buildupchao
 * @date 2019/2/1 01:25
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    private static final int EXPIRE_SECONDS = 86400;

    @Autowired
    private ZnsClientConfiguration configuration;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(configuration.getZkAddr(), 5000);
    }

    @Bean
    public LoadingCache<String, List<ProviderService>> buildCache() {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, List<ProviderService>>() {
                    @Override
                    public List<ProviderService> load(String key) throws Exception {
                        return null;
                    }
                });
    }
}
