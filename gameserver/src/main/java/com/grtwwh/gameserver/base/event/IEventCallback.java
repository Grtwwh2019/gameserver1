package com.grtwwh.gameserver.base.event;

/**
 * 事件回调
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/5/24 17:30
 */
public interface IEventCallback {

    /**
     * 事件执行后的回调，每个监听者都可能触发一次回调
     *
     * @param returnMsg 监听者处理事件后的返回值，每个监听者的返回值都可能不同
     */
    void callback(Object returnMsg);
}
