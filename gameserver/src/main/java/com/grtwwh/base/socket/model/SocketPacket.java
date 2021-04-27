package com.grtwwh.base.socket.model;


import java.util.Arrays;

/**
 * 协议包
 * packetId + length + data
 *     4        1
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 15:55
 */
public class SocketPacket {

    private int packetId;

    private byte[] data;

    public static SocketPacket valueOf(int packetId, byte[] data) {
        SocketPacket socketPacket = new SocketPacket();
        socketPacket.packetId = packetId;
        socketPacket.data = data;
        return socketPacket;
    }

    public int getPacketId() {
        return packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SocketPacket{" +
                "packetId=" + packetId +
                ", data=" + new String(data) +
                '}';
    }
}
