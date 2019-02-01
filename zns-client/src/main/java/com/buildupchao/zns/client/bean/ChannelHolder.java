package com.buildupchao.zns.client.bean;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/2/1 13:13
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelHolder {
    private Channel channel;
    private EventLoopGroup eventLoopGroup;
}
