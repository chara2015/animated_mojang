package net.minecraft.util.profiling;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ContinuousProfiler.class */
public class ContinuousProfiler {
    private final LongSupplier realTime;
    private final IntSupplier tickCount;
    private final BooleanSupplier suppressWarnings;
    private ProfileCollector profiler = InactiveProfiler.INSTANCE;

    public ContinuousProfiler(LongSupplier $$0, IntSupplier $$1, BooleanSupplier $$2) {
        this.realTime = $$0;
        this.tickCount = $$1;
        this.suppressWarnings = $$2;
    }

    public boolean isEnabled() {
        return this.profiler != InactiveProfiler.INSTANCE;
    }

    public void disable() {
        this.profiler = InactiveProfiler.INSTANCE;
    }

    public void enable() {
        this.profiler = new ActiveProfiler(this.realTime, this.tickCount, this.suppressWarnings);
    }

    public ProfilerFiller getFiller() {
        return this.profiler;
    }

    public ProfileResults getResults() {
        return this.profiler.getResults();
    }
}
