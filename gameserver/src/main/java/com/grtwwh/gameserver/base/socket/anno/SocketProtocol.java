package com.grtwwh.gameserver.base.socket.anno;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络协议包
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 10:25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketProtocol {

    /**
     * 协议Id
     */
    int packetId() default 0;

    /**
     * protobuf编译后的java类
     * @return
     */
    Class protoClass();

}
