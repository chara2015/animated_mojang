package net.labymod.api.util.concurrent.watchdog;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.management.ThreadInfo;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/watchdog/DeadlockInfo.class */
public final class DeadlockInfo extends Record {
    private final ThreadInfo[] threadInfos;
    private final Instant detectedAt;

    public DeadlockInfo(ThreadInfo[] threadInfos, Instant detectedAt) {
        this.threadInfos = threadInfos;
        this.detectedAt = detectedAt;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DeadlockInfo.class), DeadlockInfo.class, "threadInfos;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->threadInfos:[Ljava/lang/management/ThreadInfo;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DeadlockInfo.class), DeadlockInfo.class, "threadInfos;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->threadInfos:[Ljava/lang/management/ThreadInfo;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DeadlockInfo.class, Object.class), DeadlockInfo.class, "threadInfos;detectedAt", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->threadInfos:[Ljava/lang/management/ThreadInfo;", "FIELD:Lnet/labymod/api/util/concurrent/watchdog/DeadlockInfo;->detectedAt:Ljava/time/Instant;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ThreadInfo[] threadInfos() {
        return this.threadInfos;
    }

    public Instant detectedAt() {
        return this.detectedAt;
    }
}
