package com.cn.maitian.dev.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by zxd on 2017/10/23.
 */
public class ThreadUtil {
    //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
    //线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    /**创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()**/
    private static int threadMax = Runtime.getRuntime().availableProcessors();
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadMax*3);
    /***创建一个定长线程池，支持定时及周期性任务执行，***/
    public static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    public static ExecutorService getFixedThreadPool(){
            return fixedThreadPool;
    }
    public static ScheduledExecutorService getScheduledThreadPool(){
        return scheduledThreadPool;
    }
    public static ExecutorService getCachedThreadPool(){
        return cachedThreadPool;
    }
}
