package net.minecraft.util.profiling.metrics.storage;

import java.time.Instant;
import net.minecraft.util.profiling.ProfileResults;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/storage/RecordedDeviation.class */
public final class RecordedDeviation {
    public final Instant timestamp;
    public final int tick;
    public final ProfileResults profilerResultAtTick;

    public RecordedDeviation(Instant $$0, int $$1, ProfileResults $$2) {
        this.timestamp = $$0;
        this.tick = $$1;
        this.profilerResultAtTick = $$2;
    }
}
