package com.buildupchao.zns.server.acceptor.handler;

import com.buildupchao.zns.common.bean.ZnsResponse;
import com.buildupchao.zns.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author buildupchao
 *         Date: 2019/1/31 21:42
 * @since JDK 1.8
 */
public class ZnsServerEncodeHandler extends MessageToByteEncoder<ZnsResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ZnsResponse znsResponse, ByteBuf byteBuf)
            throws Exception {
        byte[] bytes = SerializationUtil.serialize(znsResponse);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
