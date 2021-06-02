package com.grtwwh.gameserver.base.event;

import java.lang.reflect.Method;

/**
 * 方法调用包装
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 18:09
 */
public class MethodInvokeWrapper {
    private Method method;
    /**
     * bean Obj
     */
    private Object obj;
    /**
     * submit Event时填充
     */
    private Class[] argTypes;

    public static MethodInvokeWrapper valueOf(Method method, Object obj) {
        MethodInvokeWrapper methodInvokeWrapper = new MethodInvokeWrapper();
        methodInvokeWrapper.method = method;
        methodInvokeWrapper.obj = obj;
        methodInvokeWrapper.argTypes = method.getParameterTypes();
        return methodInvokeWrapper;
    }

    public Object invoke(Object[] argVals) throws Exception {
        Object[] args = selectArgs(argVals);
        int argsSize = checkSize(args);
        if (argsSize != argTypes.length) {
            throw new IllegalArgumentException(String.format("触发事件实际参数数量出错，method=[%s],实际参数数量为：[%s]，所需参数数量为：[%s]", method.getName(), argsSize, argTypes.length));
        }
        return method.invoke(obj, args);
    }

    private Object[] selectArgs(Object[] argVals) {
        Object[] args = new Object[argTypes.length];
        for (Object argVal : argVals) {
            if (argVal == null) {
                continue;
            }
            for (int i = 0; i < argTypes.length; i++) {
                Class argType = argTypes[i];
                if (argVal.getClass().isAssignableFrom(argType)) {
                    args[i] = argVal;
                }
            }
        }
        return args;
    }

    private int checkSize(Object[] args) {
        int size = 0;
        for (Object arg : args) {
            if (arg != null) {
                size++;
            }
        }
        return size;
    }


    @Override
    public String toString() {
        return "MethodInvokeWrapper{" +
                "method=" + method.getName() +
                ", class=" + obj.getClass().getSimpleName() +
                '}';
    }

}
