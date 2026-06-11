package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ThreadingDetector.class */
public class ThreadingDetector {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final String name;
    private final Semaphore lock = new Semaphore(1);
    private final Lock stackTraceLock = new ReentrantLock();
    private volatile Thread threadThatFailedToAcquire;
    private volatile ReportedException fullException;

    public ThreadingDetector(String $$0) {
        this.name = $$0;
    }

    public void checkAndLock() {
        boolean $$0 = false;
        try {
            this.stackTraceLock.lock();
            if (!this.lock.tryAcquire()) {
                this.threadThatFailedToAcquire = Thread.currentThread();
                $$0 = true;
                this.stackTraceLock.unlock();
                try {
                    this.lock.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                throw this.fullException;
            }
            if (0 == 0) {
                this.stackTraceLock.unlock();
            }
        } catch (Throwable th) {
            if (!$$0) {
                this.stackTraceLock.unlock();
            }
            throw th;
        }
    }

    public void checkAndUnlock() {
        try {
            this.stackTraceLock.lock();
            Thread $$0 = this.threadThatFailedToAcquire;
            if ($$0 != null) {
                ReportedException $$1 = makeThreadingException(this.name, $$0);
                this.fullException = $$1;
                this.lock.release();
                throw $$1;
            }
            this.lock.release();
        } finally {
            this.stackTraceLock.unlock();
        }
    }

    public static ReportedException makeThreadingException(String $$0, Thread $$1) {
        String $$2 = (String) Stream.of((Object[]) new Thread[]{Thread.currentThread(), $$1}).filter((v0) -> {
            return Objects.nonNull(v0);
        }).map(ThreadingDetector::stackTrace).collect(Collectors.joining(Crypt.MIME_LINE_SEPARATOR));
        String $$3 = "Accessing " + $$0 + " from multiple threads";
        CrashReport $$4 = new CrashReport($$3, new IllegalStateException($$3));
        CrashReportCategory $$5 = $$4.addCategory("Thread dumps");
        $$5.setDetail("Thread dumps", $$2);
        LOGGER.error("Thread dumps: \n{}", $$2);
        return new ReportedException($$4);
    }

    private static String stackTrace(Thread $$0) {
        return $$0.getName() + ": \n\tat " + ((String) Arrays.stream($$0.getStackTrace()).map((v0) -> {
            return v0.toString();
        }).collect(Collectors.joining("\n\tat ")));
    }
}
