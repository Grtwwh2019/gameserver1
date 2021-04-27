package com.grtwwh.base.socket;

import com.grtwwh.proto.LoginProto.AccountLoginReq;
import com.grtwwh.base.socket.coder.SocketPacketDecoder;
import com.grtwwh.base.socket.coder.SocketPacketEncoder;
import com.grtwwh.base.socket.model.SocketPacket;
import com.grtwwh.game.packet.PacketId;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 16:20
 */
public class SocketClient {

    private List<Channel> channelFutureList = new ArrayList<>();

    public void start() throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(nioEventLoopGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("1", new SocketPacketEncoder());
                        ch.pipeline().addLast("2", new SocketPacketDecoder());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("localhost", 9999);
        channelFutureList.add(channelFuture.channel());
//        for (int i = 0; i < 1000; i++) {
//            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999);
//            channelFutureList.add(channelFuture);
//        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(24);
        try {
            SocketClient socketClient = new SocketClient();
            socketClient.start();
            new Thread(() -> {
                socketClient.channelFutureList.forEach(channel -> {
                    SocketPacket socketPacket = SocketPacket.valueOf(PacketId.ACCOUNT_LOGIN_REQ, AccountLoginReq.newBuilder().setAccount("xxxxx").build().toByteArray());
                    channel.writeAndFlush(socketPacket);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    socketPacket = SocketPacket.valueOf(PacketId.ACCOUNT_LOGIN_REQ1, AccountLoginReq.newBuilder().setAccount("yyyyyy").build().toByteArray());
                    channel.writeAndFlush(socketPacket);
                });
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < 10; i++) {
//            executorService.execute(() -> {
//                SocketClient socketClient = new SocketClient();
//                socketClient.start();
//            });
//        }
    }

    private static void buildClient() {
//        SocketClient socketClient = new SocketClient();
//        socketClient.start();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                for (ChannelFuture channelFuture : socketClient.channelFutureList) {
//                    Channel channel = channelFuture.channel();
//                    SocketPacket socketPacket = SocketPacket.valueOf(RandomUtils.nextInt(), String.format("%s%s%s", RandomUtils.nextInt(), "asdj", RandomUtils.nextInt()).getBytes());
//                    channel.writeAndFlush(socketPacket);
//                }
//            }
//        }, 1000, 5000);
    }

    /**
     * 出现报错：
     * Exception in thread "main" java.lang.IllegalStateException: failed to create a child event loop
     * 	at io.netty.util.concurrent.MultithreadEventExecutorGroup.<init>(MultithreadEventExecutorGroup.java:88)
     * 	at io.netty.util.concurrent.MultithreadEventExecutorGroup.<init>(MultithreadEventExecutorGroup.java:58)
     * 	at io.netty.channel.MultithreadEventLoopGroup.<init>(MultithreadEventLoopGroup.java:52)
     * 	at io.netty.channel.nio.NioEventLoopGroup.<init>(NioEventLoopGroup.java:88)
     * 	at io.netty.channel.nio.NioEventLoopGroup.<init>(NioEventLoopGroup.java:83)
     * 	at io.netty.channel.nio.NioEventLoopGroup.<init>(NioEventLoopGroup.java:64)
     * 	at io.netty.channel.nio.NioEventLoopGroup.<init>(NioEventLoopGroup.java:52)
     * 	at io.netty.channel.nio.NioEventLoopGroup.<init>(NioEventLoopGroup.java:44)
     * 	at com.grtwwh.base.socket.SocketClient.start(SocketClient.java:31)
     * 	at com.grtwwh.base.socket.SocketClient.buildClient(SocketClient.java:55)
     * 	at com.grtwwh.base.socket.SocketClient.main(SocketClient.java:49)
     * Caused by: io.netty.channel.ChannelException: failed to open a new selector
     * 	at io.netty.channel.nio.NioEventLoop.openSelector(NioEventLoop.java:181)
     * 	at io.netty.channel.nio.NioEventLoop.<init>(NioEventLoop.java:147)
     * 	at io.netty.channel.nio.NioEventLoopGroup.newChild(NioEventLoopGroup.java:138)
     * 	at io.netty.channel.nio.NioEventLoopGroup.newChild(NioEventLoopGroup.java:37)
     * 	at io.netty.util.concurrent.MultithreadEventExecutorGroup.<init>(MultithreadEventExecutorGroup.java:84)
     * 	... 10 more
     * Caused by: java.io.IOException: Unable to establish loopback connection
     * 	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:94)
     * 	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:61)
     * 	at java.security.AccessController.doPrivileged(Native Method)
     * 	at sun.nio.ch.PipeImpl.<init>(PipeImpl.java:171)
     * 	at sun.nio.ch.SelectorProviderImpl.openPipe(SelectorProviderImpl.java:50)
     * 	at java.nio.channels.Pipe.open(Pipe.java:155)
     * 	at sun.nio.ch.WindowsSelectorImpl.<init>(WindowsSelectorImpl.java:127)
     * 	at sun.nio.ch.WindowsSelectorProvider.openSelector(WindowsSelectorProvider.java:44)
     * 	at io.netty.channel.nio.NioEventLoop.openSelector(NioEventLoop.java:179)
     * 	... 14 more
     * Caused by: java.net.SocketException: No buffer space available (maximum connections reached?): connect
     * 	at sun.nio.ch.Net.connect0(Native Method)
     * 	at sun.nio.ch.Net.connect(Net.java:454)
     * 	at sun.nio.ch.Net.connect(Net.java:446)
     * 	at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:648)
     * 	at java.nio.channels.SocketChannel.open(SocketChannel.java:189)
     * 	at sun.nio.ch.PipeImpl$Initializer$LoopbackConnector.run(PipeImpl.java:127)
     * 	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:76)
     * 	... 22 more
     *
     * 确保没有创建太多的NioEventLoopGroups.通常，创建单个事件循环组实例并在所有通道中重用它就可以了。
     */
}
