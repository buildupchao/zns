package com.buildupchao.zns.client.connector;

import com.buildupchao.zns.client.bean.ChannelHolder;
import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.connector.init.ZnsClientInitializer;
import com.buildupchao.zns.client.runner.ZnsRequestManager;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author buildupchao
 *         Date: 2019/2/1 03:04
 * @since JDK 1.8
 */
public class ZnsClientConnector implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsClientConnector.class);

    private String requestId;
    private ProviderService providerService;
    private CountDownLatch latch;
    private ZnsClientInitializer znsClientInitializer;

    public ZnsClientConnector(String requestId, ProviderService providerService, CountDownLatch latch) {
        this.requestId = requestId;
        this.providerService = providerService;
        this.latch = latch;
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

        try {
            ChannelFuture future = bootstrap.connect().sync();
            if (future.isSuccess()) {
                ChannelHolder channelHolder = ChannelHolder.builder()
                        .channel(future.channel())
                        .eventLoopGroup(worker)
                        .build();
                ZnsRequestManager.registerChannelHolder(requestId, channelHolder);
                LOGGER.info("Construct a connector with service provider[{}:{}] successfully",
                        providerService.getServerIp(),
                        providerService.getNetworkPort()
                );

                latch.countDown();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
