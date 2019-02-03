package com.buildupchao.zns.monitor.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/2/4 05:48
 * @since JDK 1.8
 */
@Configuration
public class ZnsMonitorConfig {

    @Autowired
    private ZnsMonitorConfigProperties properties;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(properties.getZkAddr(), 5000);
    }


    @Bean
    public LoadingCache<String, List<String>> buildCache() {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String s) throws Exception {
                        return null;
                    }
                });
    }
}
