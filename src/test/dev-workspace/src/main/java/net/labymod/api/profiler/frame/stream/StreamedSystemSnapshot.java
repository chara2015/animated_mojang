package net.labymod.api.profiler.frame.stream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/StreamedSystemSnapshot.class */
public final class StreamedSystemSnapshot {
    private static final double BYTES_TO_MB = 1048576.0d;
    private static final double PERCENTAGE_MULTIPLIER = 100.0d;
    private static final double UNAVAILABLE = -1.0d;
    private final long heapUsedBytes;
    private final long heapMaxBytes;
    private final long heapCommittedBytes;
    private final long nonHeapUsedBytes;
    private final long systemTotalMemoryBytes;
    private final long systemFreeMemoryBytes;
    private final double systemLoadAverage;
    private final int availableProcessors;
    private final double cpuTemperature;
    private final double batteryLevel;

    StreamedSystemSnapshot(long heapUsedBytes, long heapMaxBytes, long heapCommittedBytes, long nonHeapUsedBytes, long systemTotalMemoryBytes, long systemFreeMemoryBytes, double systemLoadAverage, int availableProcessors, double cpuTemperature, double batteryLevel) {
        this.heapUsedBytes = heapUsedBytes;
        this.heapMaxBytes = heapMaxBytes;
        this.heapCommittedBytes = heapCommittedBytes;
        this.nonHeapUsedBytes = nonHeapUsedBytes;
        this.systemTotalMemoryBytes = systemTotalMemoryBytes;
        this.systemFreeMemoryBytes = systemFreeMemoryBytes;
        this.systemLoadAverage = systemLoadAverage;
        this.availableProcessors = availableProcessors;
        this.cpuTemperature = cpuTemperature;
        this.batteryLevel = batteryLevel;
    }

    public long getHeapUsedBytes() {
        return this.heapUsedBytes;
    }

    public double getHeapUsedMB() {
        return this.heapUsedBytes / BYTES_TO_MB;
    }

    public long getHeapMaxBytes() {
        return this.heapMaxBytes;
    }

    public double getHeapMaxMB() {
        return this.heapMaxBytes / BYTES_TO_MB;
    }

    public long getHeapCommittedBytes() {
        return this.heapCommittedBytes;
    }

    public double getHeapCommittedMB() {
        return this.heapCommittedBytes / BYTES_TO_MB;
    }

    public long getNonHeapUsedBytes() {
        return this.nonHeapUsedBytes;
    }

    public double getNonHeapUsedMB() {
        return this.nonHeapUsedBytes / BYTES_TO_MB;
    }

    public double getHeapUsagePercent() {
        if (this.heapMaxBytes <= 0) {
            return UNAVAILABLE;
        }
        return (this.heapUsedBytes * PERCENTAGE_MULTIPLIER) / this.heapMaxBytes;
    }

    public long getSystemTotalMemoryBytes() {
        return this.systemTotalMemoryBytes;
    }

    public double getSystemTotalMemoryMB() {
        return this.systemTotalMemoryBytes / BYTES_TO_MB;
    }

    public long getSystemFreeMemoryBytes() {
        return this.systemFreeMemoryBytes;
    }

    public double getSystemFreeMemoryMB() {
        return this.systemFreeMemoryBytes / BYTES_TO_MB;
    }

    public double getSystemMemoryUsagePercent() {
        if (this.systemTotalMemoryBytes <= 0) {
            return UNAVAILABLE;
        }
        long used = this.systemTotalMemoryBytes - this.systemFreeMemoryBytes;
        return (used * PERCENTAGE_MULTIPLIER) / this.systemTotalMemoryBytes;
    }

    public double getSystemLoadAverage() {
        return this.systemLoadAverage;
    }

    public int getAvailableProcessors() {
        return this.availableProcessors;
    }

    public double getCpuTemperature() {
        return this.cpuTemperature;
    }

    public double getBatteryLevel() {
        return this.batteryLevel;
    }

    public boolean hasSystemMemoryInfo() {
        return this.systemTotalMemoryBytes > 0;
    }
}
