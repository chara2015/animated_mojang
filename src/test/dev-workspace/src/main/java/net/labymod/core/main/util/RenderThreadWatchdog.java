package net.labymod.core.main.util;

import java.lang.management.ThreadInfo;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.watchdog.DeadlockInfo;
import net.labymod.api.util.concurrent.watchdog.HangInfo;
import net.labymod.api.util.concurrent.watchdog.ThreadWatchdog;
import net.labymod.api.util.concurrent.watchdog.WatchedThread;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.user.shop.spray.SprayConstants;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/RenderThreadWatchdog.class */
public final class RenderThreadWatchdog {

    @Nullable
    private static volatile ThreadWatchdog watchdog;

    @Nullable
    private static volatile WatchedThread handle;

    @Nullable
    private static volatile Thread renderThread;
    private static final Logging LOGGER = Logging.getLogger();
    private static final Object LOCK = new Object();
    private static volatile Profile currentProfile = Profile.NORMAL;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/RenderThreadWatchdog$Profile.class */
    public enum Profile {
        RELAXED(SprayConstants.LABYMOD_PLUS_NEXT_SPRAY, 10000, false, false),
        NORMAL(1000, 3000, true, true),
        AGGRESSIVE(250, 1000, true, true),
        PARANOID(100, 500, true, true);

        private final long checkIntervalMillis;
        private final long timeoutMillis;
        private final boolean captureStackTraces;
        private final boolean detectDeadlocks;

        Profile(long checkIntervalMillis, long timeoutMillis, boolean captureStackTraces, boolean detectDeadlocks) {
            this.checkIntervalMillis = checkIntervalMillis;
            this.timeoutMillis = timeoutMillis;
            this.captureStackTraces = captureStackTraces;
            this.detectDeadlocks = detectDeadlocks;
        }
    }

    private RenderThreadWatchdog() {
    }

    public static void initialize(Thread thread) {
        synchronized (LOCK) {
            if (renderThread != null) {
                return;
            }
            renderThread = thread;
            applyProfile(currentProfile);
        }
    }

    public static void setProfile(Profile profile) {
        synchronized (LOCK) {
            if (currentProfile != profile || watchdog == null) {
                currentProfile = profile;
                if (renderThread == null) {
                    return;
                }
                applyProfile(profile);
                LOGGER.info("Render thread watchdog profile set to {} (check={}ms, timeout={}ms, stacks={}, deadlocks={})", profile.name(), Long.valueOf(profile.checkIntervalMillis), Long.valueOf(profile.timeoutMillis), Boolean.valueOf(profile.captureStackTraces), Boolean.valueOf(profile.detectDeadlocks));
            }
        }
    }

    public static Profile profile() {
        return currentProfile;
    }

    public static void heartbeat() {
        WatchedThread current = handle;
        if (current != null) {
            current.heartbeat();
        }
    }

    public static void shutdown() {
        synchronized (LOCK) {
            tearDown();
            renderThread = null;
        }
    }

    private static void applyProfile(Profile profile) {
        tearDown();
        Thread thread = renderThread;
        if (thread == null) {
            return;
        }
        ThreadWatchdog built = ThreadWatchdog.builder().checkInterval(profile.checkIntervalMillis, TimeUnit.MILLISECONDS).defaultTimeout(profile.timeoutMillis, TimeUnit.MILLISECONDS).captureStackTraces(profile.captureStackTraces).detectDeadlocks(profile.detectDeadlocks).onHang(RenderThreadWatchdog::onHang).onRecovery(RenderThreadWatchdog::onRecovery).onDeadlock(RenderThreadWatchdog::onDeadlock).name("RenderThreadWatchdog").build();
        built.start();
        watchdog = built;
        handle = built.watch("Render Thread", thread).timeout(profile.timeoutMillis, TimeUnit.MILLISECONDS).register();
    }

    private static void tearDown() {
        WatchedThread currentHandle = handle;
        if (currentHandle != null) {
            currentHandle.unregister();
            handle = null;
        }
        ThreadWatchdog current = watchdog;
        if (current != null) {
            current.stop();
            watchdog = null;
        }
    }

    private static void onHang(HangInfo info) {
        long durationSec = info.hangDuration() / 1000;
        Thread thread = info.thread();
        LOGGER.error("========== RENDER THREAD HANG DETECTED ==========", new Object[0]);
        LOGGER.error("Thread '{}' (id={}, state={}) unresponsive for {}s", info.threadName(), Long.valueOf(thread.threadId()), thread.getState(), Long.valueOf(durationSec));
        StackTraceElement[] trace = info.stackTrace();
        if (trace.length > 0) {
            LOGGER.error("Stack trace at time of detection:", new Object[0]);
            for (StackTraceElement element : trace) {
                LOGGER.error("    at {}", element);
            }
        }
        LOGGER.error("==================================================", new Object[0]);
    }

    private static void onRecovery(HangInfo info) {
        LOGGER.info("Thread '{}' recovered (was unresponsive, last heartbeat {}ms ago)", info.threadName(), Long.valueOf(info.hangDuration()));
    }

    private static void onDeadlock(DeadlockInfo info) {
        LOGGER.error("========== DEADLOCK DETECTED ==========", new Object[0]);
        LOGGER.error("{} threads involved:", Integer.valueOf(info.threadInfos().length));
        for (ThreadInfo threadInfo : info.threadInfos()) {
            LOGGER.error("  Thread '{}' (id={}) blocked on {} held by '{}'", threadInfo.getThreadName(), Long.valueOf(threadInfo.getThreadId()), threadInfo.getLockName(), threadInfo.getLockOwnerName());
            StackTraceElement[] trace = threadInfo.getStackTrace();
            for (StackTraceElement element : trace) {
                LOGGER.error("      at {}", element);
            }
        }
        LOGGER.error("=======================================", new Object[0]);
    }
}
