package com.buildupchao.zns.client;

import com.buildupchao.zns.client.pull.ServicePullManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author buildupchao
 *         Date: 2019/2/1 00:56
 * @since JDK 1.8
 */
@Component
public class ZnsClientRunner {

    @Autowired
    private ServicePullManager servicePullManager;

    @PostConstruct
    public void run() {
        servicePullManager.pullServiceFromZK();
    }
}
