package com.buildupchao.zns.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/1/31 20:36
 * @since JDK 1.8
 */
@Data
@Component
public class ZnsServerConfiguration {

    @Value("${zns.server.zk.root}")
    private String zkRoot;

    @Value("${zns.server.zk.addr}")
    private String zkAddr;

    @Value("${zns.server.zk.switch}")
    private boolean zkSwitch;

    @Value("${zns.network.port}")
    private int networkPort;

    @Value("${server.port}")
    private int serverPort;
}
