package com.grtwwh.gameserver.base.event;

import java.lang.reflect.Method;

/**
 * 事件总线
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 15:21
 */
public interface IEventBus {

    /**
     * 注册事件
     */
    void register(Class eventClass, Method method, Object obj);

    /**
     * 通知观察者事件发生
     */
    void submitSync(Class eventClass, IEventCallback eventCallback, Object... args);

    void submit(Class eventClass, IEventCallback eventCallback, int dispatcherCode, Object... args);
}
