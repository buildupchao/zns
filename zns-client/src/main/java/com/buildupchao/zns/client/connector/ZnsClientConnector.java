package com.buildupchao.zns.client.connector;

import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.connector.init.ZnsClientInitializer;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 *         Date: 2019/2/1 03:04
 * @since JDK 1.8
 */
public class ZnsClientConnector implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsClientConnector.class);

    private ProviderService providerService;
    private ZnsClientInitializer znsClientInitializer;

    public ZnsClientConnector(ProviderService providerService) {
        this.providerService = providerService;
        this.znsClientInitializer = SpringBeanFactory.getBean(ZnsClientInitializer.class);
    }

    @Override
    public void run() {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .remoteAddress(providerService.getServerIp(), providerService.getNetworkPort())
                .handler(znsClientInitializer);

        bootstrap.connect().channel();
    }
}
