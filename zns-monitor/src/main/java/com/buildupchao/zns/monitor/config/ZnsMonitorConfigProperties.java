package com.buildupchao.zns.monitor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/2/4 05:46
 * @since JDK 1.8
 */
@Data
@Component
public class ZnsMonitorConfigProperties {

    @Value("${zns.monitor.zk.root}")
    private String zkRoot;

    @Value("${zns.monitor.zk.addr}")
    private String zkAddr;

    @Value("${zns.monitor.zk.switch}")
    private boolean zkSwitch;

    @Value("${server.port}")
    private int port;
}
