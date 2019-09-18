package com.buildupchao.zns.server.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author buildupchao
 * @date 2019/1/31 20:52
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Autowired
    private ZnsServerConfiguration znsServerConfiguration;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(znsServerConfiguration.getZkAddr(), 5000);
    }
}
