package com.buildupchao.zns.server.acceptor.handler;

import com.buildupchao.zns.common.bean.ZnsRequest;
import com.buildupchao.zns.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author buildupchao
 * @date 2019/1/31 21:39
 * @since JDK 1.8
 */
public class ZnsServerDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws
            Exception {
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
            ZnsRequest znsRequest = SerializationUtil.deserialize(bytes, ZnsRequest.class);
            list.add(znsRequest);
        }
    }
}
