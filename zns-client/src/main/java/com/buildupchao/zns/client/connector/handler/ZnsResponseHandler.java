package com.buildupchao.zns.client.connector.handler;

import com.buildupchao.zns.client.runner.ZnsRequestPool;
import com.buildupchao.zns.common.bean.ZnsResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 * @date 2019/2/1 03:06
 * @since JDK 1.8
 */
@Component
@ChannelHandler.Sharable
public class ZnsResponseHandler extends SimpleChannelInboundHandler<ZnsResponse> {

    @Autowired
    private ZnsRequestPool requestPool;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ZnsResponse znsResponse) throws Exception {
        requestPool.notifyRequest(znsResponse.getRequestId(), znsResponse);
    }
}
