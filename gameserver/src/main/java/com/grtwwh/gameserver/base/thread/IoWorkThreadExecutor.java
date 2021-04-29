package com.grtwwh.gameserver.base.thread;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Io线程池
 * 频繁IO，可能会经常阻塞且操作时间长，需要多条线程
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 11:28
 */
public class IoWorkThreadExecutor {

    private static ScheduledExecutorService scheduledExecutorService;

    public static void init(int corePoolSize) {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize, new DefaultThreadFactory("IoWorkExecutor"));
    }

    public static void addTask(Runnable runnable) {
        scheduledExecutorService.execute(runnable);
    }

    public static ScheduledFuture<?> addScheduleTask(long delay, TimeUnit timeUnit, Runnable runnable) {
        return scheduledExecutorService.schedule(runnable, delay, timeUnit);
    }

    public static ScheduledFuture<?> addScheduleWithFixedDelay(long initialDelay, long delay, TimeUnit timeUnit, Runnable runnable) {
        return scheduledExecutorService.scheduleWithFixedDelay(runnable, initialDelay, delay, timeUnit);
    }

    public static ScheduledFuture<?> addScheduleAtFixedRate(long initialDelay, long period, TimeUnit timeUnit, Runnable runnable) {
        return scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }
}
