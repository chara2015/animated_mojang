package net.labymod.api.util.time;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/TimeSource.class */
public interface TimeSource extends LongSupplier {
    long get(TimeUnit timeUnit);
}
