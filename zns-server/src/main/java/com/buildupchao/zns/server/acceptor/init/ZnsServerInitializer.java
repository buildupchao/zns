package com.buildupchao.zns.server.acceptor.init;

import com.buildupchao.zns.server.acceptor.handler.ZnsRequestHandler;
import com.buildupchao.zns.server.acceptor.handler.ZnsServerDecodeHandler;
import com.buildupchao.zns.server.acceptor.handler.ZnsServerEncodeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/1/31 21:35
 * @since JDK 1.8
 */
@Component
@ChannelHandler.Sharable
public class ZnsServerInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private ZnsRequestHandler znsRequestHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new ZnsServerDecodeHandler())
                .addLast(new ZnsServerEncodeHandler())
                .addLast(znsRequestHandler);
    }
}
