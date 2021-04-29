package com.grtwwh.gameserver.base.socket.session;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/27 17:09
 */
@Component
public class SocketSessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketSessionManager.class);

    private Map<Channel, SocketSession> sessionMap = new ConcurrentHashMap<>();

    public void sessionActive(Channel channel) {
        SocketSession socketSession = sessionMap.computeIfAbsent(channel, c -> SocketSession.valueOf(channel));
        LOGGER.info(String.format("用户：ip-[%s]连接了服务器", socketSession.getSocketAddress()));
    }

    public void sessionInactive(Channel channel) {
        SocketSession socketSession = sessionMap.remove(channel);
        LOGGER.info(String.format("用户：ip-[%s]断开了服务器", socketSession.getSocketAddress()));
    }

    public SocketSession getSession(Channel channel) {
        SocketSession socketSession = sessionMap.get(channel);
        if (socketSession == null) {
            // 不存在session
            socketSession = new SocketSession();
        }
        return socketSession;
    }
}
