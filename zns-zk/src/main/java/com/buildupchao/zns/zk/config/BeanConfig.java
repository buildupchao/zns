package com.buildupchao.zns.zk.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author buildupchao
 * @date 2019/2/17 17:40
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Autowired
    private ZnsZkConfiguration configuration;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(configuration.getZkAddr(), configuration.getZkConnectTimeout());
    }
}
