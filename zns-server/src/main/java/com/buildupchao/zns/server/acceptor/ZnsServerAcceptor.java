package com.buildupchao.zns.server.acceptor;

import com.buildupchao.zns.server.acceptor.init.ZnsServerInitializer;
import com.buildupchao.zns.server.config.ZnsServerConfiguration;
import com.buildupchao.zns.server.util.SpringBeanFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZnsServer Acceptor:
 * Netty acceptor which provides network service.
 *
 * @author buildupchao
 *         Date: 2019/1/31 21:23
 * @since JDK 1.8
 */
public class ZnsServerAcceptor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsServerAcceptor.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();

    private ZnsServerConfiguration znsServerConfiguration;
    private ZnsServerInitializer znsServerInitializer;

    public ZnsServerAcceptor() {
        this.znsServerConfiguration = SpringBeanFactory.getBean(ZnsServerConfiguration.class);
        this.znsServerInitializer = SpringBeanFactory.getBean(ZnsServerInitializer.class);
    }

    @Override
    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(znsServerInitializer);

        try {
            ChannelFuture future = bootstrap.bind(znsServerConfiguration.getAcceptorPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("ZnsServer acceptor startup failure!", e);
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully().syncUninterruptibly();
            worker.shutdownGracefully().syncUninterruptibly();
        }
    }
}
