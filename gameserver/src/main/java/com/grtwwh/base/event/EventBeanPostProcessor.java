package com.grtwwh.base.event;

import com.grtwwh.base.socket.anno.SocketProtocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 事件注册处理器
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 15:22
 */
@Component
public class EventBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    @Autowired
    private EventBus eventBus;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventReceiver.class) != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                // @EventReceiver方法参数数量 <= 2
                if (parameterTypes.length < 0 || parameterTypes.length > 2) {
                    throw new IllegalArgumentException("@EventReceiver方法参数数量出错");
                }
                Class<?> parameterType = parameterTypes[0];
                if (parameterType.isAssignableFrom(IEvent.class)) {
                    // 事件注册
                } else {
                    // 协议注册 SocketProtocol -> method
                    for (Class<?> type : parameterTypes) {
                        if (type.getAnnotation(SocketProtocol.class) != null) {
                            parameterType = type;
                            break;
                        }
                    }
                }
                eventBus.register(parameterType, method, bean);
            }
        }
        return super.postProcessAfterInstantiation(bean, beanName);
    }
}
