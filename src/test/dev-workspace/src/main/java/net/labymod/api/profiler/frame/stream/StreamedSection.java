package net.labymod.api.profiler.frame.stream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/StreamedSection.class */
public final class StreamedSection {
    private static final double NANOS_TO_MILLIS = 1000000.0d;
    private final String name;
    private final long accumulatedTimeNanos;
    private final long selfTimeNanos;
    private final int callCount;
    private final long minCallTimeNanos;
    private final long maxCallTimeNanos;
    private final StreamedSection[] children;

    StreamedSection(String name, long accumulatedTimeNanos, long selfTimeNanos, int callCount, long minCallTimeNanos, long maxCallTimeNanos, StreamedSection[] children) {
        this.name = name;
        this.accumulatedTimeNanos = accumulatedTimeNanos;
        this.selfTimeNanos = selfTimeNanos;
        this.callCount = callCount;
        this.minCallTimeNanos = minCallTimeNanos;
        this.maxCallTimeNanos = maxCallTimeNanos;
        this.children = children;
    }

    public String getName() {
        return this.name;
    }

    public String getBaseName() {
        int hashIndex = this.name.lastIndexOf(35);
        if (hashIndex > 0) {
            return this.name.substring(0, hashIndex);
        }
        return this.name;
    }

    public long getAccumulatedTimeNanos() {
        return this.accumulatedTimeNanos;
    }

    public double getDurationMillis() {
        return this.accumulatedTimeNanos / NANOS_TO_MILLIS;
    }

    public long getSelfTimeNanos() {
        return this.selfTimeNanos;
    }

    public double getSelfTimeMillis() {
        return this.selfTimeNanos / NANOS_TO_MILLIS;
    }

    public int getCallCount() {
        return this.callCount;
    }

    public long getMinCallTimeNanos() {
        return this.minCallTimeNanos;
    }

    public double getMinCallTimeMillis() {
        return this.minCallTimeNanos / NANOS_TO_MILLIS;
    }

    public long getMaxCallTimeNanos() {
        return this.maxCallTimeNanos;
    }

    public double getMaxCallTimeMillis() {
        return this.maxCallTimeNanos / NANOS_TO_MILLIS;
    }

    public double getAvgCallTimeMillis() {
        if (this.callCount == 0) {
            return 0.0d;
        }
        return getDurationMillis() / ((double) this.callCount);
    }

    public StreamedSection[] getChildren() {
        return this.children;
    }

    public int getChildCount() {
        return this.children.length;
    }
}
