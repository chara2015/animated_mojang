package net.minecraft.world;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/Stopwatch.class */
public final class Stopwatch extends Record {
    private final long creationTime;
    private final long accumulatedElapsedTime;

    public Stopwatch(long $$0, long $$1) {
        this.creationTime = $$0;
        this.accumulatedElapsedTime = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Stopwatch.class), Stopwatch.class, "creationTime;accumulatedElapsedTime", "FIELD:Lnet/minecraft/world/Stopwatch;->creationTime:J", "FIELD:Lnet/minecraft/world/Stopwatch;->accumulatedElapsedTime:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Stopwatch.class), Stopwatch.class, "creationTime;accumulatedElapsedTime", "FIELD:Lnet/minecraft/world/Stopwatch;->creationTime:J", "FIELD:Lnet/minecraft/world/Stopwatch;->accumulatedElapsedTime:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Stopwatch.class, Object.class), Stopwatch.class, "creationTime;accumulatedElapsedTime", "FIELD:Lnet/minecraft/world/Stopwatch;->creationTime:J", "FIELD:Lnet/minecraft/world/Stopwatch;->accumulatedElapsedTime:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public long creationTime() {
        return this.creationTime;
    }

    public long accumulatedElapsedTime() {
        return this.accumulatedElapsedTime;
    }

    public Stopwatch(long $$0) {
        this($$0, 0L);
    }

    public long elapsedMilliseconds(long $$0) {
        long $$1 = $$0 - this.creationTime;
        return this.accumulatedElapsedTime + $$1;
    }

    public double elapsedSeconds(long $$0) {
        return elapsedMilliseconds($$0) / 1000.0d;
    }
}
