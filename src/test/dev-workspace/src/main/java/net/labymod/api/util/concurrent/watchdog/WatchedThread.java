package net.labymod.api.util.concurrent.watchdog;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/WatchedThread.class */
public final class WatchedThread {
    private final String name;
    private final Thread thread;
    private final long timeoutMillis;
    private final Consumer<HangInfo> hangHandler;
    private final Consumer<HangInfo> recoveryHandler;
    private final boolean captureStackTraces;
    private final AtomicLong lastHeartbeat = new AtomicLong(System.currentTimeMillis());
    private volatile boolean hanging = false;
    private final ThreadWatchdog owner;

    WatchedThread(String name, Thread thread, long timeoutMillis, Consumer<HangInfo> hangHandler, Consumer<HangInfo> recoveryHandler, boolean captureStackTraces, ThreadWatchdog owner) {
        this.name = name;
        this.thread = thread;
        this.timeoutMillis = timeoutMillis;
        this.hangHandler = hangHandler;
        this.recoveryHandler = recoveryHandler;
        this.captureStackTraces = captureStackTraces;
        this.owner = owner;
    }

    public void heartbeat() {
        this.lastHeartbeat.set(System.currentTimeMillis());
    }

    public void unregister() {
        this.owner.unregister(this);
    }

    public String name() {
        return this.name;
    }

    public Thread thread() {
        return this.thread;
    }

    public long timeoutMillis() {
        return this.timeoutMillis;
    }

    Consumer<HangInfo> hangHandler() {
        return this.hangHandler;
    }

    Consumer<HangInfo> recoveryHandler() {
        return this.recoveryHandler;
    }

    boolean captureStackTraces() {
        return this.captureStackTraces;
    }

    long lastHeartbeat() {
        return this.lastHeartbeat.get();
    }

    public boolean isHanging() {
        return this.hanging;
    }

    void setHanging(boolean hanging) {
        this.hanging = hanging;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/WatchedThread$Builder.class */
    public static final class Builder {
        private final String name;
        private final Thread thread;
        private final ThreadWatchdog watchdog;
        private long timeoutMillis;
        private Consumer<HangInfo> hangHandler;
        private Consumer<HangInfo> recoveryHandler;
        private Boolean captureStackTraces;

        Builder(String name, Thread thread, ThreadWatchdog watchdog, long defaultTimeoutMillis) {
            this.name = name;
            this.thread = thread;
            this.watchdog = watchdog;
            this.timeoutMillis = defaultTimeoutMillis;
        }

        public Builder timeout(long time, TimeUnit unit) {
            this.timeoutMillis = unit.toMillis(time);
            return this;
        }

        public Builder onHang(Consumer<HangInfo> handler) {
            this.hangHandler = handler;
            return this;
        }

        public Builder onRecovery(Consumer<HangInfo> handler) {
            this.recoveryHandler = handler;
            return this;
        }

        public Builder captureStackTraces(boolean capture) {
            this.captureStackTraces = Boolean.valueOf(capture);
            return this;
        }

        public WatchedThread register() {
            boolean zCaptureStackTraces;
            if (this.timeoutMillis <= 0) {
                throw new IllegalArgumentException("timeout must be positive");
            }
            if (this.captureStackTraces != null) {
                zCaptureStackTraces = this.captureStackTraces.booleanValue();
            } else {
                zCaptureStackTraces = this.watchdog.captureStackTraces();
            }
            boolean capture = zCaptureStackTraces;
            WatchedThread watched = new WatchedThread(this.name, this.thread, this.timeoutMillis, this.hangHandler, this.recoveryHandler, capture, this.watchdog);
            this.watchdog.addWatched(watched);
            return watched;
        }
    }
}
