package com.buildupchao.zns.server;

import com.buildupchao.zns.server.acceptor.ZnsServerAcceptor;
import com.buildupchao.zns.server.push.ServicePushManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manage Center:
 * Start netty acceptor which provides network service.
 * Register services that owns @ZnsService annotation into zookeeper.
 * Start HeartBeatChecker, keep alive with zookeeper.
 *
 * @author buildupchao
 * @date 2019/1/31 20:34
 * @since JDK 1.8
 */
@Component
public class ZnsServerRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsServerRunner.class);

    private static ExecutorService executor = null;

    @Autowired
    private ServicePushManager servicePushManager;

    public void run() {
        executor = Executors.newFixedThreadPool(3);

        // Start Acceptor，waiting for the service caller to fire the request call
        executor.execute(new ZnsServerAcceptor());

        // Register service providers into Zookeeper
        servicePushManager.registerIntoZK();
    }


    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
