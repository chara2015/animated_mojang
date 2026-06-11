package net.labymod.api.util.concurrent.watchdog;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.labymod.api.util.concurrent.watchdog.WatchedThread;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/ThreadWatchdog.class */
public final class ThreadWatchdog {
    private static final long DEFAULT_CHECK_INTERVAL_MS = 1000;
    private static final long DEFAULT_TIMEOUT_MS = 5000;
    private final long checkIntervalMillis;
    private final long defaultTimeoutMillis;
    private final boolean captureStackTraces;
    private final boolean detectDeadlocks;
    private final Consumer<HangInfo> globalHangHandler;
    private final Consumer<HangInfo> globalRecoveryHandler;
    private final Consumer<DeadlockInfo> deadlockHandler;
    private final String name;
    private final boolean daemon;
    private volatile Thread watchdogThread;
    private static final Logging LOGGER = Logging.getLogger();
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
    private final List<WatchedThread> watchedThreads = new CopyOnWriteArrayList();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Set<Long> lastReportedDeadlockIds = Set.of();

    ThreadWatchdog(long checkIntervalMillis, long defaultTimeoutMillis, boolean captureStackTraces, boolean detectDeadlocks, Consumer<HangInfo> globalHangHandler, Consumer<HangInfo> globalRecoveryHandler, Consumer<DeadlockInfo> deadlockHandler, String name, boolean daemon) {
        this.checkIntervalMillis = checkIntervalMillis;
        this.defaultTimeoutMillis = defaultTimeoutMillis;
        this.captureStackTraces = captureStackTraces;
        this.detectDeadlocks = detectDeadlocks;
        this.globalHangHandler = globalHangHandler;
        this.globalRecoveryHandler = globalRecoveryHandler;
        this.deadlockHandler = deadlockHandler;
        this.name = name;
        this.daemon = daemon;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void start() {
        if (!this.running.compareAndSet(false, true)) {
            throw new IllegalStateException("Watchdog '" + this.name + "' is already running");
        }
        this.watchdogThread = new Thread(this::runLoop, this.name);
        this.watchdogThread.setDaemon(this.daemon);
        this.watchdogThread.start();
    }

    public void stop() {
        Thread thread;
        if (this.running.compareAndSet(true, false) && (thread = this.watchdogThread) != null) {
            thread.interrupt();
            try {
                thread.join(this.checkIntervalMillis * 2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.watchdogThread = null;
        }
    }

    public WatchedThread.Builder watch(String name, Thread thread) {
        return new WatchedThread.Builder(name, thread, this, this.defaultTimeoutMillis);
    }

    public WatchedThread watchAndRegister(String name, Thread thread) {
        return watch(name, thread).register();
    }

    public boolean isRunning() {
        return this.running.get();
    }

    boolean captureStackTraces() {
        return this.captureStackTraces;
    }

    public List<WatchedThread> watchedThreads() {
        return Collections.unmodifiableList(this.watchedThreads);
    }

    void addWatched(WatchedThread watched) {
        this.watchedThreads.add(watched);
    }

    void unregister(WatchedThread watched) {
        this.watchedThreads.remove(watched);
    }

    private void runLoop() {
        ThreadMXBean threadMXBean;
        if (this.detectDeadlocks) {
            threadMXBean = ManagementFactory.getThreadMXBean();
        } else {
            threadMXBean = null;
        }
        ThreadMXBean mxBean = threadMXBean;
        while (this.running.get()) {
            try {
                checkThreads();
                if (mxBean != null) {
                    checkDeadlocks(mxBean);
                }
                Thread.sleep(this.checkIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e2) {
                LOGGER.error("Error in watchdog '{}' check loop", this.name, e2);
            }
        }
    }

    private void checkThreads() {
        long now = System.currentTimeMillis();
        for (WatchedThread watched : this.watchedThreads) {
            long elapsed = now - watched.lastHeartbeat();
            boolean overdue = elapsed > watched.timeoutMillis();
            if (overdue && !watched.isHanging()) {
                watched.setHanging(true);
                HangInfo info = createHangInfo(watched, elapsed);
                fireHandler(watched.hangHandler(), this.globalHangHandler, info, "hang");
            } else if (!overdue && watched.isHanging()) {
                watched.setHanging(false);
                HangInfo info2 = createHangInfo(watched, elapsed);
                fireHandler(watched.recoveryHandler(), this.globalRecoveryHandler, info2, "recovery");
            }
        }
    }

    private void checkDeadlocks(ThreadMXBean mxBean) {
        long[] deadlockedIds = mxBean.findDeadlockedThreads();
        if (deadlockedIds == null) {
            this.lastReportedDeadlockIds = Set.of();
            return;
        }
        Set<Long> currentIds = new HashSet<>(deadlockedIds.length);
        for (long id : deadlockedIds) {
            currentIds.add(Long.valueOf(id));
        }
        if (currentIds.equals(this.lastReportedDeadlockIds)) {
            return;
        }
        this.lastReportedDeadlockIds = currentIds;
        ThreadInfo[] threadInfos = mxBean.getThreadInfo(deadlockedIds, true, true);
        DeadlockInfo info = new DeadlockInfo(threadInfos, Instant.now());
        if (this.deadlockHandler != null) {
            try {
                this.deadlockHandler.accept(info);
            } catch (Exception e) {
                LOGGER.error("Error in deadlock handler for watchdog '{}'", this.name, e);
            }
        }
    }

    private HangInfo createHangInfo(WatchedThread watched, long elapsed) {
        StackTraceElement[] stackTrace;
        if (watched.captureStackTraces()) {
            stackTrace = watched.thread().getStackTrace();
        } else {
            stackTrace = EMPTY_STACK_TRACE;
        }
        StackTraceElement[] stackTrace2 = stackTrace;
        return new HangInfo(watched.name(), watched.thread(), elapsed, stackTrace2, Instant.now());
    }

    private void fireHandler(Consumer<HangInfo> perThread, Consumer<HangInfo> global, HangInfo info, String label) {
        Consumer<HangInfo> handler = perThread != null ? perThread : global;
        if (handler != null) {
            try {
                handler.accept(info);
            } catch (Exception e) {
                LOGGER.error("Error in {} handler for thread '{}'", label, info.threadName(), e);
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/ThreadWatchdog$Builder.class */
    public static final class Builder {
        private boolean detectDeadlocks;
        private Consumer<HangInfo> globalHangHandler;
        private Consumer<HangInfo> globalRecoveryHandler;
        private Consumer<DeadlockInfo> deadlockHandler;
        private long checkIntervalMillis = 1000;
        private long defaultTimeoutMillis = ThreadWatchdog.DEFAULT_TIMEOUT_MS;
        private boolean captureStackTraces = true;
        private String name = "ThreadWatchdog";
        private boolean daemon = true;

        Builder() {
        }

        public Builder checkInterval(long time, TimeUnit unit) {
            this.checkIntervalMillis = unit.toMillis(time);
            return this;
        }

        public Builder defaultTimeout(long time, TimeUnit unit) {
            this.defaultTimeoutMillis = unit.toMillis(time);
            return this;
        }

        public Builder captureStackTraces(boolean capture) {
            this.captureStackTraces = capture;
            return this;
        }

        public Builder detectDeadlocks(boolean detect) {
            this.detectDeadlocks = detect;
            return this;
        }

        public Builder onHang(Consumer<HangInfo> handler) {
            this.globalHangHandler = handler;
            return this;
        }

        public Builder onRecovery(Consumer<HangInfo> handler) {
            this.globalRecoveryHandler = handler;
            return this;
        }

        public Builder onDeadlock(Consumer<DeadlockInfo> handler) {
            this.deadlockHandler = handler;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder daemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public ThreadWatchdog build() {
            if (this.checkIntervalMillis <= 0) {
                throw new IllegalArgumentException("checkInterval must be positive");
            }
            if (this.defaultTimeoutMillis <= 0) {
                throw new IllegalArgumentException("defaultTimeout must be positive");
            }
            return new ThreadWatchdog(this.checkIntervalMillis, this.defaultTimeoutMillis, this.captureStackTraces, this.detectDeadlocks, this.globalHangHandler, this.globalRecoveryHandler, this.deadlockHandler, this.name, this.daemon);
        }
    }
}
