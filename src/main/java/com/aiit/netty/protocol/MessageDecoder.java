package com.aiit.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        final int length = internalBuffer().readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        MessageProtocol messageProtocol = new MessageProtocol(length, bytes);
        list.add(messageProtocol);
    }
}


