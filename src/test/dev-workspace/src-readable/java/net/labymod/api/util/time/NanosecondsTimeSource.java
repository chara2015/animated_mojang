package net.labymod.api.util.time;

import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/NanosecondsTimeSource.class */
@FunctionalInterface
public interface NanosecondsTimeSource extends TimeSource {
    @Override // net.labymod.api.util.time.TimeSource
    default long get(TimeUnit unit) {
        return unit.convert(getAsLong(), TimeUnit.NANOSECONDS);
    }
}
