package net.labymod.api.util.concurrent.watchdog;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/HangInfo.class */
public final class HangInfo extends Record {
    private final String threadName;
    private final Thread thread;
    private final long hangDuration;
    private final StackTraceElement[] stackTrace;
    private final Instant detectedAt;

    public HangInfo(String threadName, Thread thread, long hangDuration, StackTraceElement[] stackTrace, Instant detectedAt) {
        this.threadName = threadName;
        this.thread = thread;
        this.hangDuration = hangDuration;
        this.stackTrace = stackTrace;
        this.detectedAt = detectedAt;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, HangInfo.class), HangInfo.class, "threadName;thread;hangDuration;stackTrace;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->threadName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->thread:Ljava/lang/Thread;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->hangDuration:J", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->stackTrace:[Ljava/lang/StackTraceElement;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, HangInfo.class), HangInfo.class, "threadName;thread;hangDuration;stackTrace;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->threadName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->thread:Ljava/lang/Thread;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->hangDuration:J", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->stackTrace:[Ljava/lang/StackTraceElement;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, HangInfo.class, Object.class), HangInfo.class, "threadName;thread;hangDuration;stackTrace;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->threadName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->thread:Ljava/lang/Thread;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->hangDuration:J", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->stackTrace:[Ljava/lang/StackTraceElement;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/HangInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String threadName() {
        return this.threadName;
    }

    public Thread thread() {
        return this.thread;
    }

    public long hangDuration() {
        return this.hangDuration;
    }

    public StackTraceElement[] stackTrace() {
        return this.stackTrace;
    }

    public Instant detectedAt() {
        return this.detectedAt;
    }
}
