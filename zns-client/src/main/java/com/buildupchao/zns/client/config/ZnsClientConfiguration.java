package com.buildupchao.zns.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/2/1 01:17
 * @since JDK 1.8
 */
@Data
@Component
public class ZnsClientConfiguration {

    @Value("${zns.client.zk.root}")
    private String zkRoot;

    @Value("${zns.client.zk.addr}")
    private String zkAddr;

    @Value("${server.port}")
    private String znsClientPort;
}
