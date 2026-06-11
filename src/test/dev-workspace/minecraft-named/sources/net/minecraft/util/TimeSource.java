package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/TimeSource.class */
@FunctionalInterface
public interface TimeSource {
    long get(TimeUnit timeUnit);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/TimeSource$NanoTimeSource.class */
    public interface NanoTimeSource extends TimeSource, LongSupplier {
        @Override // net.minecraft.util.TimeSource
        default long get(TimeUnit $$0) {
            return $$0.convert(getAsLong(), TimeUnit.NANOSECONDS);
        }
    }
}
