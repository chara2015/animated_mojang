package net.minecraft.util.profiling.metrics.profiling;

import net.minecraft.util.profiling.ProfilerFiller;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/profiling/MetricsRecorder.class */
public interface MetricsRecorder {
    void end();

    void cancel();

    void startTick();

    boolean isRecording();

    ProfilerFiller getProfiler();

    void endTick();
}
