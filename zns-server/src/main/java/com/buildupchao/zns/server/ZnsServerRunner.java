package com.buildupchao.zns.server;

import com.buildupchao.zns.server.acceptor.ZnsServerAcceptor;
import com.buildupchao.zns.server.push.ServicePushManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
 *         Date: 2019/1/31 20:34
 * @since JDK 1.8
 */
@Component
public class ZnsServerRunner {

    private static ExecutorService executor = null;

    @Autowired
    private ServicePushManager servicePushManager;

    @PostConstruct
    public void run() {
        executor = Executors.newFixedThreadPool(3);

        // 启动Acceptor，等待服务调用者发起调用请求
        executor.execute(new ZnsServerAcceptor());

        // 注册服务提供者信息列表到Zookeeper
        servicePushManager.registerIntoZK();
    }


    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
