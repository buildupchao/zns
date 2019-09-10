package com.buildupchao.zns.client.connector.handler;

import com.buildupchao.zns.common.bean.ZnsRequest;
import com.buildupchao.zns.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author buildupchao
 * @date 2019/2/1 03:07
 * @since JDK 1.8
 */
public class ZnsClientEncodeHandler extends MessageToByteEncoder<ZnsRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ZnsRequest znsRequest, ByteBuf in) throws Exception {
        byte[] bytes = SerializationUtil.serialize(znsRequest);
        in.writeInt(bytes.length);
        in.writeBytes(bytes);
    }
}
