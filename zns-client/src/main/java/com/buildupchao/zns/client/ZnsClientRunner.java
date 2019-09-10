package com.buildupchao.zns.client;

import com.buildupchao.zns.client.cache.ServiceRouteCache;
import com.buildupchao.zns.client.proxy.ServiceProxyManager;
import com.buildupchao.zns.client.pull.ServicePullManager;
import com.buildupchao.zns.client.runner.ZnsRequestManager;
import com.buildupchao.zns.client.runner.ZnsRequestPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/2/1 00:56
 * @since JDK 1.8
 */
@Component
public class ZnsClientRunner {

    @Autowired
    private ServicePullManager servicePullManager;

    @Autowired
    private ServiceProxyManager serviceProxyManager;

    @Autowired
    private ZnsRequestPool znsRequestPool;

    @Autowired
    private ServiceRouteCache serviceRouteCache;

    public void run() {
        // Start request manager
        ZnsRequestManager.startZnsRequestManager(znsRequestPool, serviceRouteCache);

        // Pull service provider info from zookeeper
        servicePullManager.pullServiceFromZK();

        // Create proxy for service which owns @ZnsClient annotation
        serviceProxyManager.initServiceProxyInstance();
    }
}
