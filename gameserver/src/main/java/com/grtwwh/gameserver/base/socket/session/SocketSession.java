package com.grtwwh.gameserver.base.socket.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络会话
 * 封装原因：
 * - 将通道和玩家对象（player）建立连接，统一管理
 * - 管理所有连接服务器的通道（日志等）
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/27 16:54
 */
public class SocketSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketSession.class);

    private static AtomicInteger idIncreaseTool = new AtomicInteger(1);

    /**
     * 连接服务器的主体
     * 一般情况下： channel -> player
     */
    private static final AttributeKey<Object> MAIN = AttributeKey.valueOf("MAIN");

    private int id;

    private Channel channel;

    private int dispatcherCode;

    private SocketAddress socketAddress;

    public static SocketSession valueOf(Channel channel) {
        SocketSession socketSession = new SocketSession();
        socketSession.id = idIncreaseTool.getAndIncrement();
        socketSession.channel = channel;
        socketSession.dispatcherCode = channel.id().hashCode();
        socketSession.socketAddress = channel.remoteAddress();
        return socketSession;
    }

    public int getId() {
        return id;
    }

    public Object getMain() {
        Object main = channel.attr(MAIN).get();
        return main == null ? this : main;
    }

    public void bindMain(Object main) {
        this.channel.attr(MAIN).set(main);
    }

    public int getDispatcherCode() {
        return dispatcherCode;
    }

    public void setDispatcherCode(int dispatcherCode) {
        this.dispatcherCode = dispatcherCode;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
