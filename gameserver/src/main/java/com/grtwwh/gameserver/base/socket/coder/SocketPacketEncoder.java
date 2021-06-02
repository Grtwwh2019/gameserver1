package com.grtwwh.gameserver.base.socket.coder;

import com.grtwwh.gameserver.base.socket.model.SocketPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Socket协议包编码器
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 11:44
 */
public class SocketPacketEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        if (!msg.getClass().isAssignableFrom(SocketPacket.class)) {
            throw new IllegalArgumentException(String.format("输出对象类型出错：%s", msg.getClass()));
        }
        SocketPacket socketPacket = (SocketPacket) msg;
        out.writeInt(socketPacket.getPacketId());
        out.writeInt(socketPacket.getData().length);
        out.writeBytes(socketPacket.getData());
    }

}
