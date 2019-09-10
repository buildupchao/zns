package com.buildupchao.zns.client.connector.handler;

import com.buildupchao.zns.common.bean.ZnsResponse;
import com.buildupchao.zns.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author buildupchao
 * @date 2019/2/1 03:07
 * @since JDK 1.8
 */
public class ZnsClientDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() <= 4) {
            return;
        }

        int length = in.readInt();
        in.markReaderIndex();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
        } else {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            ZnsResponse znsResponse = SerializationUtil.deserialize(bytes, ZnsResponse.class);
            list.add(znsResponse);
        }
    }
}
