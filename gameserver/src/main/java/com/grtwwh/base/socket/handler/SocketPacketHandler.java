package com.grtwwh.base.socket.handler;

import com.grtwwh.base.event.EventBus;
import com.grtwwh.base.socket.anno.SocketProtocol;
import com.grtwwh.base.socket.model.SocketPacket;
import com.grtwwh.base.socket.session.SocketSession;
import com.grtwwh.base.socket.session.SocketSessionManager;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Socket协议包处理器
 * <p>
 * Sharable注解：在多个ChannelPipeline中共享同一个ChannelHandler，便于收集跨越多个Channel的统计信息等操作。
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 11:48
 */
@Sharable
@Component
public class SocketPacketHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketPacketHandler.class);

    @Autowired
    private SocketSessionManager socketSessionManager;

    private ApplicationContext applicationContext;

    private Map<Integer, Object> packetId2Parser = new ConcurrentHashMap<>();
    private Map<Integer, Object> packetId2Protocol = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SocketProtocol.class);
        for (Entry<String, Object> beanEntry : beansWithAnnotation.entrySet()) {
            // xxxReq pojo
            Object protocol = beanEntry.getValue();
            SocketProtocol socketProtocol = protocol.getClass().getAnnotation(SocketProtocol.class);
            // 保存pojo class
            packetId2Protocol.computeIfAbsent(socketProtocol.packetId(), key -> protocol);
            // proto类协议
            Class protoClass = socketProtocol.protoClass();
            // 保存proto解析器
            Object parser = protoClass.getDeclaredMethod("parser").invoke(null);
            packetId2Parser.computeIfAbsent(socketProtocol.packetId(), key -> parser);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        socketSessionManager.sessionActive(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!msg.getClass().isAssignableFrom(SocketPacket.class)) {
            return;
        }
        SocketPacket socketPacket = (SocketPacket) msg;
        int packetId = socketPacket.getPacketId();
        byte[] data = socketPacket.getData();
        // 数据解码
        Object protocol = decodeProto(packetId, data);
        if (protocol == null) {
            return;
        }
        // 根据packetId找到对应的协议处理（facade.method），实际上是进行转发
        // 转发protocol
        try {
            SocketSession session = socketSessionManager.getSession(ctx.channel());
            EventBus.getInstance().submit(protocol.getClass(), session.getDispatcherCode(), session.getMain(), protocol);
        } catch (Exception e) {
            LOGGER.error(String.format("[%s]-消息转发失败", protocol.getClass()), e);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        socketSessionManager.sessionInactive(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * protobuf进行解码
     *
     * @param packetId
     * @param data     protobuf编码数据
     * @return 解码后的pojo对象
     */
    private Object decodeProto(int packetId, byte[] data) {
        Object parser = packetId2Parser.get(packetId);
        if (parser == null) {
            LOGGER.error(String.format("不存在packetId=[%s]的协议解析器", packetId));
            return null;
        }
        // 解析proto类
        Object proto = parseProto(parser, data);
        // proto转换为 xxxReq pojo
        return transferProto2Pojo(packetId, proto);
    }

    /**
     * 解析proto类
     *
     * @param parser proto解析器
     * @param data   proto二进制格式数据
     * @return proto类
     */
    private Object parseProto(Object parser, byte[] data) {
        try {
            Method parseFrom = parser.getClass().getMethod("parseFrom", byte[].class);
            // 解析proto类
            return parseFrom.invoke(parser, data);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("解析proto类时出错", e);
        }
        return null;
    }

    /**
     * proto转换为 xxxReq pojo
     *
     * @param packetId
     * @param proto
     * @return pojo
     */
    private Object transferProto2Pojo(int packetId, Object proto) {
        try {
            Object protocol = packetId2Protocol.get(packetId);
            for (Field field : protocol.getClass().getDeclaredFields()) {
                String fieldName = field.getName() + "_";
                field.setAccessible(true);
                Field protoField = proto.getClass().getDeclaredField(fieldName);
                protoField.setAccessible(true);
                field.set(protocol, protoField.get(proto));
            }
            return protocol;
        } catch (Exception e) {
            LOGGER.error("proto转换为pojo时出错", e);
        }
        return null;
    }

}
