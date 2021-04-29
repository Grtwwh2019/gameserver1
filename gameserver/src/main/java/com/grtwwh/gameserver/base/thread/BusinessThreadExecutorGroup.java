package com.grtwwh.gameserver.base.thread;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.SingleThreadEventExecutor;

import java.util.concurrent.*;

/**
 * 业务线程池（单线程池组）
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 11:28
 */
public class BusinessThreadExecutorGroup {

    private static ScheduledExecutorService[] executorGroup;

    public static void init(int threads) {
        executorGroup = new ScheduledExecutorService[threads];
        for (int i = 0; i < threads; i++) {
            executorGroup[i] = new BusinessThreadExecutor();
        }
    }

    private static int getExecutorIndex(int dispatcherCode) {
        return Math.abs(dispatcherCode % executorGroup.length);
    }

    public static void addTask(int dispatcherCode, Runnable runnable) {
        int index = getExecutorIndex(dispatcherCode);
        executorGroup[index].execute(runnable);
    }

    public static ScheduledFuture<?> addScheduleTask(int dispatcherCode, long delay, TimeUnit timeUnit, Runnable runnable) {
        int index = getExecutorIndex(dispatcherCode);
        return executorGroup[index].schedule(runnable, delay, timeUnit);
    }

    public static ScheduledFuture<?> addScheduleWithFixedDelay(int dispatcherCode, long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
        int index = getExecutorIndex(dispatcherCode);
        return executorGroup[index].scheduleWithFixedDelay(runnable, initialDelay, delay, timeUnit);
    }

    public static ScheduledFuture<?> addScheduleAtFixedRate(int dispatcherCode, long initialDelay, long period, TimeUnit timeUnit, Runnable runnable) {
        int index = getExecutorIndex(dispatcherCode);
        return executorGroup[index].scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    private static class BusinessThreadExecutor extends SingleThreadEventExecutor {

        protected BusinessThreadExecutor() {
            super(null, new DefaultThreadFactory("BusinessThreadExecutor"), true);
        }

        @Override
        protected void run() {
            do {
                Runnable task = takeTask();
                if (task != null) {
                    task.run();
                }
                // 保证线程池一直运行
            } while (!confirmShutdown());
        }


    }
}
