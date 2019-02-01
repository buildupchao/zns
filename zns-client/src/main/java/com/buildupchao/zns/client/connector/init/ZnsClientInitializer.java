package com.buildupchao.zns.client.connector.init;

import com.buildupchao.zns.client.connector.handler.ZnsClientDecodeHandler;
import com.buildupchao.zns.client.connector.handler.ZnsClientEncodeHandler;
import com.buildupchao.zns.client.connector.handler.ZnsResponseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/2/1 03:25
 * @since JDK 1.8
 */
@Component
@ChannelHandler.Sharable
public class ZnsClientInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private ZnsResponseHandler znsResponseHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new ZnsClientEncodeHandler())
                .addLast(new ZnsClientDecodeHandler())
                .addLast(znsResponseHandler);
    }
}
