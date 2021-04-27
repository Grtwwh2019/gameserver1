package com.grtwwh.base.socket.coder;

import com.grtwwh.base.socket.model.SocketPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Socket协议包解码器
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 11:45
 */
public class SocketPacketDecoder extends ByteToMessageDecoder {

    private static final int MAX_LENGTH = 1 * 1024 * 1024;

    private static final int MIN_LENGTH = 5;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // packetId + length + data
        //     4        1
        int readableBytes = in.readableBytes();
        // 超出
        if (readableBytes > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("协议长度溢出，大小：[%s]", readableBytes));
        }
        // 无数据
        if (readableBytes < MIN_LENGTH) {
            throw new IllegalArgumentException(String.format("协议异常，大小：[%s]", readableBytes));
        }
        // 记录协议包的起始位置
        in.markReaderIndex();
        int packetId = in.readInt();
        byte len = in.readByte();
        if (len < 0) {
            throw new IllegalArgumentException(String.format("数据异常，大小：[%s]", len));
        }
        // 比较协议数据大小和 剩余可读数据大小
        if (in.readableBytes() < len) {
            // 一个消息分拆成多次读取
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[len];
        in.readBytes(data);
        SocketPacket socketPacket = SocketPacket.valueOf(packetId, data);
        out.add(socketPacket);
    }
}
