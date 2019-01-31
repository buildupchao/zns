package com.buildupchao.zns.client.config;

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
 *         Date: 2019/2/1 01:25
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
    public LoadingCache<String, List<String>> buildCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(3000)
                .expireAfterWrite(EXPIRE_SECONDS, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String key) throws Exception {
                        return null;
                    }
                });
    }
}
