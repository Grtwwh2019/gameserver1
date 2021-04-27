package com.grtwwh.game;

import com.grtwwh.base.event.EventBus;
import com.grtwwh.base.socket.SocketServer;
import com.grtwwh.base.thread.BusinessThreadExecutorGroup;
import com.grtwwh.base.thread.IoWorkThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务器启动类
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/25 15:50
 */
public class Start {

    private static final Logger LOGGER = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 启动游戏服务器连接
        SocketServer socketServer = applicationContext.getBean(SocketServer.class);
        socketServer.start();
        // 初始化线程池
        BusinessThreadExecutorGroup.init(24);
        IoWorkThreadExecutor.init(100);
        while (applicationContext.isActive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("服务器主线程被非法打断", e);
            }
        }
        LOGGER.info("服务器已关闭");
    }


}
