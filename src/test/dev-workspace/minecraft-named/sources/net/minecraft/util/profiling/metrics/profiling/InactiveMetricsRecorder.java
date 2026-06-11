package net.minecraft.util.profiling.metrics.profiling;

import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfilerFiller;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/profiling/InactiveMetricsRecorder.class */
public class InactiveMetricsRecorder implements MetricsRecorder {
    public static final MetricsRecorder INSTANCE = new InactiveMetricsRecorder();

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public void end() {
    }

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public void cancel() {
    }

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public void startTick() {
    }

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public boolean isRecording() {
        return false;
    }

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public ProfilerFiller getProfiler() {
        return InactiveProfiler.INSTANCE;
    }

    @Override // net.minecraft.util.profiling.metrics.profiling.MetricsRecorder
    public void endTick() {
    }
}
