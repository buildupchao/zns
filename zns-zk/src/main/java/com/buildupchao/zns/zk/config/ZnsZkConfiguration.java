package com.buildupchao.zns.zk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/2/17 17:38
 * @since JDK 1.8
 */
@Data
@Component
public class ZnsZkConfiguration {

    @Value("${zns.zk.root}")
    private String zkRoot;

    @Value("${zns.zk.addr}")
    private String zkAddr;

    @Value("${zns.zk.switch}")
    private boolean zkSwitch;

    @Value("${zns.zk.connect.timeout:5000}")
    private int zkConnectTimeout;
}
