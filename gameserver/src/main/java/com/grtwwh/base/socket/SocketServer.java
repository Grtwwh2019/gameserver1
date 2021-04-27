package com.grtwwh.base.socket;

import com.grtwwh.base.socket.coder.SocketPacketDecoder;
import com.grtwwh.base.socket.coder.SocketPacketEncoder;
import com.grtwwh.base.socket.handler.SocketPacketHandler;
import com.grtwwh.base.socket.model.SocketPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 网络通信服务器
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/21 17:23
 */
@Component
public class SocketServer {

    /**
     * Requirements：
     * version 1.0：
     * 1.只要能够成功连接即可；
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    @Autowired
    private SocketPacketHandler socketPacketHandler;
    /**
     * 服务器地址
     */
    private String host;

    /**
     * 服务器端口
     */
    private int port;

    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private ChannelFuture channelFuture;

    private void bind() throws InterruptedException {
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("idle", new IdleStateHandler(1000, 0, 0, TimeUnit.MILLISECONDS));
                        // 编码器 出站数据编码(s -> c) 对于json来说，编码即，obj -> json，解码即，json -> obj
                        pipeline.addLast("encoder", new SocketPacketEncoder());
                        // 解码器 入站数据解码(c -> s)
                        pipeline.addLast("decoder", new SocketPacketDecoder());
                        // channel处理器
                        pipeline.addLast("socketPacketHandler", socketPacketHandler);
                    }
                });
        channelFuture = serverBootstrap.bind(9999);
    }

    private void shutdown() {
        try {
            if (channelFuture.channel() != null) {
                channelFuture.channel().close();
            }
        } catch (Exception e) {
            LOGGER.error("server channel关闭异常", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void start() {
        try {
            bind();
        } catch (InterruptedException e) {
            LOGGER.error(String.format("网络中断，原因：%s", e.getMessage()), e);
        }
        LOGGER.info(String.format("启动服务器成功：[port]-%s", 9999));
    }
}
