package net.labymod.api.concurrent;

import java.lang.Thread;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/concurrent/ThreadFactoryBuilder.class */
public class ThreadFactoryBuilder {
    private String nameFormat;
    private boolean daemon;
    private int priority;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private ThreadFactory backingThreadFactory;

    public ThreadFactoryBuilder withNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
        return this;
    }

    public ThreadFactoryBuilder withDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ThreadFactoryBuilder withUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public ThreadFactoryBuilder withBackingThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    public ThreadFactory build() {
        ThreadFactory threadFactoryDefaultThreadFactory;
        String nameFormat = this.nameFormat;
        boolean daemon = this.daemon;
        int priority = this.priority;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler;
        if (this.backingThreadFactory != null) {
            threadFactoryDefaultThreadFactory = this.backingThreadFactory;
        } else {
            threadFactoryDefaultThreadFactory = Executors.defaultThreadFactory();
        }
        ThreadFactory backingThreadFactory = threadFactoryDefaultThreadFactory;
        AtomicLong count = nameFormat != null ? new AtomicLong(0L) : null;
        return r -> {
            Thread thread = backingThreadFactory.newThread(r);
            if (nameFormat != null) {
                thread.setName(String.format(Locale.ROOT, nameFormat, Long.valueOf(count.getAndIncrement())));
            }
            thread.setDaemon(daemon);
            if (priority <= 10 && priority >= 1) {
                thread.setPriority(priority);
            }
            if (uncaughtExceptionHandler != null) {
                thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
            return thread;
        };
    }
}
