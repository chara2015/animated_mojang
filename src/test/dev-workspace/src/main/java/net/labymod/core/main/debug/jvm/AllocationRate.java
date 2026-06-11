package net.labymod.core.main.debug.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Locale;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/jvm/AllocationRate.class */
public final class AllocationRate {
    private static final int DEFAULT_UPDATE_INTERVAL = 500;
    private static final List<GarbageCollectorMXBean> GC_BEANS = ManagementFactory.getGarbageCollectorMXBeans();
    private static final long ONE_SECOND = 1000;
    private final long updateInterval;
    private long lastTime;
    private long lastHeapUsage;
    private long lastGarbageCollectorCounts;
    private long lastRate;

    public AllocationRate() {
        this(500L);
    }

    public AllocationRate(long updateInterval) {
        this.lastHeapUsage = -1L;
        this.lastGarbageCollectorCounts = -1L;
        this.updateInterval = updateInterval;
    }

    public long getBytesAllocatedPerSecond(long value) {
        long currentTime = TimeUtil.getCurrentTimeMillis();
        if (currentTime - this.lastTime < this.updateInterval) {
            return this.lastRate;
        }
        long counts = getGarbageCollectorCounts();
        if (this.lastTime != 0 && counts == this.lastGarbageCollectorCounts) {
            double diffTime = 1000.0d / (currentTime - this.lastTime);
            long diff = value - this.lastHeapUsage;
            this.lastRate = Math.round(diff * diffTime);
        }
        this.lastTime = currentTime;
        this.lastHeapUsage = value;
        this.lastGarbageCollectorCounts = counts;
        return this.lastRate;
    }

    public long getMegaBytesAllocatedPerSecond(long value) {
        return (getBytesAllocatedPerSecond(value) / 1024) / 1024;
    }

    public String getAllocationRate() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long allocationRate = totalMemory - freeMemory;
        return String.format(Locale.ROOT, "%03dMB /s", Long.valueOf(getMegaBytesAllocatedPerSecond(allocationRate)));
    }

    private long getGarbageCollectorCounts() {
        long count = 0;
        for (GarbageCollectorMXBean bean : GC_BEANS) {
            count += bean.getCollectionCount();
        }
        return count;
    }
}
