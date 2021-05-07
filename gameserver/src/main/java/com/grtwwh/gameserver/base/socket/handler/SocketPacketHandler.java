package com.grtwwh.gameserver.base.socket.handler;

import com.grtwwh.gameserver.base.event.EventBus;
import com.grtwwh.gameserver.base.socket.anno.SocketProtocol;
import com.grtwwh.gameserver.base.socket.model.SocketPacket;
import com.grtwwh.gameserver.base.socket.session.SocketSession;
import com.grtwwh.gameserver.base.socket.session.SocketSessionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.HashedWheelTimer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    private static SocketPacketHandler instance;

    @Autowired
    private SocketSessionManager socketSessionManager;

    private ApplicationContext applicationContext;
    /**
     * packetId, protobuf.parser
     */
    private Map<Integer, Object> packetId2Parser = new ConcurrentHashMap<>();
    /**
     * packetId, xxxReq(pojo)
     */
    private Map<Integer, Object> packetId2Protocol = new ConcurrentHashMap<>();
    /**
     * Req.class, packetId
     */
    private Map<Class, Integer> protocolClass2PacketId = new ConcurrentHashMap<>();
    /**
     * 定时器
     */
    private HashedWheelTimer delayFlushTimer = new HashedWheelTimer();

    public SocketPacketHandler() {
        instance = this;
    }

    @PostConstruct
    private void init() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SocketProtocol.class);
        for (Entry<String, Object> beanEntry : beansWithAnnotation.entrySet()) {
            // xxxReq pojo
            Object protocol = beanEntry.getValue();
            SocketProtocol socketProtocol = protocol.getClass().getAnnotation(SocketProtocol.class);
            protocolClass2PacketId.put(protocol.getClass(), socketProtocol.packetId());
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

    public static SocketPacketHandler getInstance() {
        return instance;
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
     * 解析proto类, byte 2 protobuf
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
     * pojo -> protobuf byte[]
     *
     * @param protocol
     * @param parser   protobuf parser
     * @return
     */
    private byte[] toByteArray(Object protocol, Object parser) throws Exception {
        // toByteArray
        ParameterizedType genericSuperclass = (ParameterizedType) parser.getClass().getGenericSuperclass();
        // protobuf.class
        Type actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
        Class<?> protoClass = Class.forName(actualTypeArgument.getTypeName());
        Object builder = protoClass.getDeclaredMethod("newBuilder").invoke(null);
        for (Field declaredField : builder.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            String fileName = StringUtils.left(declaredField.getName(), declaredField.getName().length() - 1);
            Field protocolField = protocol.getClass().getDeclaredField(fileName);
            protocolField.setAccessible(true);
            declaredField.set(builder, protocolField.get(protocol));
        }
        Object proto = builder.getClass().getDeclaredMethod("build").invoke(builder);
        return (byte[]) proto.getClass().getMethod("toByteArray").invoke(proto);
    }

    /**
     * proto转换为 xxxReq pojo
     *
     * @param packetId
     * @param proto
     * @return pojo
     */
    private Object transferProto2Pojo(int packetId, Object proto) {
        Object protocol = packetId2Protocol.get(packetId);
        try {
            for (Field field : protocol.getClass().getDeclaredFields()) {
                resolveFields(field, proto, protocol);
            }
            return protocol;
        } catch (Exception e) {
            LOGGER.error(String.format("proto转换为pojo时出错, %s -> %s", proto.getClass().getName(), protocol.getClass().getName()), e);
        }
        return null;
    }

    /**
     * 处理字段赋值
     *
     * @param protocolField
     * @param proto
     * @param protocol
     * @throws Exception
     */
    private void resolveFields(Field protocolField, Object proto, Object protocol) throws Exception {
        String fieldName = protocolField.getName() + "_";
        Field protoField = proto.getClass().getDeclaredField(fieldName);
        protoField.setAccessible(true);
        Object value = protoField.get(proto);
        protocolField.setAccessible(true);
        if (protocolField.getType().isPrimitive()
                || protocolField.getType().isEnum()
                || protocolField.getType().equals(String.class)) {
            protocolField.set(protocol, value);
        } else {
            Field[] declaredFields = protocolField.getType().getDeclaredFields();
            Object o = protocolField.getType().newInstance();
            for (Field field : declaredFields) {
                resolveFields(field, value, o);
            }
            protocolField.set(protocol, o);
        }
    }

    /**
     * xxxReq -> protobuf
     * 编码protobuf
     *
     * @param msg
     * @return
     */
    public SocketPacket encodeProto(Object msg) throws Exception {
        if (msg instanceof SocketPacket) {
            return (SocketPacket) msg;
        }
        Class<?> protocolClass = msg.getClass();
        int packetId = protocolClass2PacketId.get(protocolClass);
        Object parser = packetId2Parser.get(packetId);
        byte[] data = toByteArray(msg, parser);
        SocketPacket socketPacket = SocketPacket.valueOf(packetId, data);
        return socketPacket;
    }

    public ChannelFuture sendPacket(Channel channel, Object packet, boolean flushNow) {
        try {
            SocketPacket socketPacket = encodeProto(packet);
            if (flushNow) {
                return channel.writeAndFlush(socketPacket);
            }
            ChannelFuture channelFuture = channel.write(socketPacket);
            delayFlushTimer.newTimeout(timeout -> {
                channel.flush();
            }, 100, TimeUnit.MILLISECONDS);
            return channelFuture;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
