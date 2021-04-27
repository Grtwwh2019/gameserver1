package com.grtwwh.base.event;

import com.google.common.collect.Lists;
import com.grtwwh.base.thread.BusinessThreadExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件总线
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 15:21
 */
@Component
public class EventBus implements IEventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    // EventClass -> EventHandler(method)
    private Map<Class, List<MethodInvokeWrapper>> event2MethodMap = new ConcurrentHashMap<>();

    private static EventBus instance;

    @PostConstruct
    private void init() {
        instance = this;
    }

    public static EventBus getInstance() {
        return instance;
    }

    @Override
    public void register(Class eventClass, Method method, Object obj) {
        List<MethodInvokeWrapper> methodInvokeWrappers = event2MethodMap.computeIfAbsent(eventClass, key -> Lists.newArrayList());
        methodInvokeWrappers.add(MethodInvokeWrapper.valueOf(method, obj));
    }

    @Override
    public void submitSync(Class eventClass, Object... args) {
        List<MethodInvokeWrapper> methodInvokeWrappers = event2MethodMap.get(eventClass);
        for (MethodInvokeWrapper methodInvokeWrapper : methodInvokeWrappers) {
            try {
                methodInvokeWrapper.invoke(args);
                //LOGGER.info(String.format("触发[%s]事件成功", methodInvokeWrapper.toString()));
            } catch (Exception e) {
                LOGGER.error(String.format("触发[%s]事件失败", methodInvokeWrapper.toString()), e);
            }
        }
    }

    @Override
    public void submit(Class eventClass, int dispatcherCode, Object... args) {
        BusinessThreadExecutorGroup.addTask(dispatcherCode, () -> {
            submitSync(eventClass, args);
        });
    }

}
