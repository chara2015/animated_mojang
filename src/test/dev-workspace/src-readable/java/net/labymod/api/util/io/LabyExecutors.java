package net.labymod.api.util.io;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.concurrent.ThreadFactoryBuilder;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/LabyExecutors.class */
public final class LabyExecutors {
    public static final int MAX_THREAD_COUNT = 255;
    private static final Logging LOGGER = Logging.getLogger();
    private static final ExecutorService BACKGROUND_EXECUTOR = makeExecutor("Main");

    private LabyExecutors() {
    }

    public static ExecutorService newSingleThreadExecutor(String nameFormat) {
        return Executors.newSingleThreadExecutor(buildThreadFactory(nameFormat));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(String nameFormat) {
        return Executors.newSingleThreadScheduledExecutor(buildThreadFactory(nameFormat));
    }

    public static ExecutorService newVirtualThreadExecutor(String name) {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name(name, 0L).factory());
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String nameFormat) {
        return Executors.newFixedThreadPool(nThreads, buildThreadFactory(nameFormat));
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, String nameFormat) {
        return Executors.newScheduledThreadPool(corePoolSize, buildThreadFactory(nameFormat));
    }

    public static int getThreadCount(int maxThreadCount) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return MathHelper.clamp(availableProcessors - 1, 1, maxThreadCount);
    }

    public static Future<?> submitBackgroundTask(Runnable task) {
        return BACKGROUND_EXECUTOR.submit(task);
    }

    public static void executeBackgroundTask(Runnable task) {
        BACKGROUND_EXECUTOR.execute(task);
    }

    public static ExecutorService getBackgroundExecutor() {
        return BACKGROUND_EXECUTOR;
    }

    private static ExecutorService makeExecutor(String name) {
        int threadCount = getThreadCount(255);
        AtomicInteger counter = new AtomicInteger(1);
        return new ForkJoinPool(threadCount, thread -> {
            ForkJoinWorkerThread worker = new ForkJoinWorkerThread(thread) { // from class: net.labymod.api.util.io.LabyExecutors.1
                @Override // java.util.concurrent.ForkJoinWorkerThread
                protected void onTermination(Throwable exception) {
                    if (exception == null) {
                        LabyExecutors.LOGGER.debug("{} shutdown", getName());
                    } else {
                        LabyExecutors.LOGGER.warn("{} died", getName());
                    }
                    super.onTermination(exception);
                }
            };
            worker.setName("LabyMod-Worker-" + name + "-" + counter.getAndIncrement());
            return worker;
        }, LabyExecutors::onThreadException, true);
    }

    private static void onThreadException(Thread thread, Throwable exception) {
        LOGGER.error("Caught exception in thread {}", thread.getName(), exception);
    }

    private static ThreadFactory buildThreadFactory(String nameFormat) {
        return new ThreadFactoryBuilder().withNameFormat(nameFormat).build();
    }
}
