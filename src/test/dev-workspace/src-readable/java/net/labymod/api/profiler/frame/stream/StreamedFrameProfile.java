package net.labymod.api.profiler.frame.stream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/StreamedFrameProfile.class */
public final class StreamedFrameProfile {
    private final long frameNumber;
    private final long durationNanos;
    private final boolean flaggedAsSlow;
    private final StreamedSystemSnapshot systemSnapshot;
    private final StreamedSection root;

    StreamedFrameProfile(long frameNumber, long durationNanos, boolean flaggedAsSlow, StreamedSystemSnapshot systemSnapshot, StreamedSection root) {
        this.frameNumber = frameNumber;
        this.durationNanos = durationNanos;
        this.flaggedAsSlow = flaggedAsSlow;
        this.systemSnapshot = systemSnapshot;
        this.root = root;
    }

    public long getFrameNumber() {
        return this.frameNumber;
    }

    public long getDurationNanos() {
        return this.durationNanos;
    }

    public double getDurationMillis() {
        return this.durationNanos / 1000000.0d;
    }

    public boolean isFlaggedAsSlow() {
        return this.flaggedAsSlow;
    }

    public StreamedSystemSnapshot getSystemSnapshot() {
        return this.systemSnapshot;
    }

    public StreamedSection getRoot() {
        return this.root;
    }

    public String buildCallTree(double thresholdMillis) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Frame #%d - %.2fms", Long.valueOf(this.frameNumber), Double.valueOf(getDurationMillis())));
        if (this.flaggedAsSlow) {
            builder.append(" [SLOW]");
        }
        builder.append("\n");
        buildCallTreeRecursive(builder, this.root, thresholdMillis, "");
        return builder.toString();
    }

    private void buildCallTreeRecursive(StringBuilder builder, StreamedSection section, double thresholdMillis, String indent) {
        StreamedSection[] children = section.getChildren();
        int childCount = children.length;
        int i = 0;
        while (i < childCount) {
            StreamedSection child = children[i];
            if (child.getDurationMillis() >= thresholdMillis) {
                boolean isLast = i == childCount - 1;
                String prefix = isLast ? "└─ " : "├─ ";
                String childIndent = indent + (isLast ? "   " : "│  ");
                builder.append(indent).append(prefix).append(child.getName());
                int callCount = child.getCallCount();
                if (callCount > 1) {
                    builder.append(String.format(" - %.2fms total (x%d calls, avg: %.2fms, min: %.2fms, max: %.2fms)", Double.valueOf(child.getDurationMillis()), Integer.valueOf(callCount), Double.valueOf(child.getAvgCallTimeMillis()), Double.valueOf(child.getMinCallTimeMillis()), Double.valueOf(child.getMaxCallTimeMillis())));
                } else {
                    builder.append(String.format(" - %.2fms (self: %.2fms)", Double.valueOf(child.getDurationMillis()), Double.valueOf(child.getSelfTimeMillis())));
                }
                builder.append("\n");
                buildCallTreeRecursive(builder, child, thresholdMillis, childIndent);
            }
            i++;
        }
    }
}
