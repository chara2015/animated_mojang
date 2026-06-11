package net.minecraft.util.debugchart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debugchart/SampleLogger.class */
public interface SampleLogger {
    void logFullSample(long[] jArr);

    void logSample(long j);

    void logPartialSample(long j, int i);
}
