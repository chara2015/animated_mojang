package net.labymod.api.profiler.frame;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameProfile.class */
public final class FrameProfile {
    private static final double NANOS_TO_MILLIS = 1000000.0d;
    private static final String ROOT_SECTION_NAME = "root";
    private static final int ROOT_DEPTH = 0;
    private static final String TREE_BRANCH_LAST = "└─ ";
    private static final String TREE_BRANCH = "├─ ";
    private static final String TREE_INDENT_LAST = "   ";
    private static final String TREE_INDENT = "│  ";
    private static final String SLOW_FRAME_MARKER = " [SLOW]";
    private final long frameNumber;
    private final ProfilerSection root;
    private long startTimeNanos;
    private long endTimeNanos;
    private boolean completed;
    private boolean flaggedAsSlow;
    private SystemSnapshot systemSnapshot;

    public FrameProfile(long frameNumber) {
        this.frameNumber = frameNumber;
        this.root = new ProfilerSection(ROOT_SECTION_NAME, 0, null);
        this.completed = false;
        this.flaggedAsSlow = false;
        this.systemSnapshot = null;
    }

    private FrameProfile(long frameNumber, ProfilerSection root, long startTimeNanos, long endTimeNanos, boolean completed, boolean flaggedAsSlow, SystemSnapshot systemSnapshot) {
        this.frameNumber = frameNumber;
        this.root = root;
        this.startTimeNanos = startTimeNanos;
        this.endTimeNanos = endTimeNanos;
        this.completed = completed;
        this.flaggedAsSlow = flaggedAsSlow;
        this.systemSnapshot = systemSnapshot;
    }

    public FrameProfile copy() {
        ProfilerSection copiedRoot = this.root.copy(null);
        return new FrameProfile(this.frameNumber, copiedRoot, this.startTimeNanos, this.endTimeNanos, this.completed, this.flaggedAsSlow, this.systemSnapshot);
    }

    public void start() {
        this.startTimeNanos = System.nanoTime();
        this.root.start();
        this.completed = false;
    }

    public void end(double slowThresholdMillis) {
        this.endTimeNanos = System.nanoTime();
        this.root.end();
        this.completed = true;
        this.flaggedAsSlow = getDurationMillis() >= slowThresholdMillis;
        if (this.flaggedAsSlow) {
            this.systemSnapshot = SystemSnapshot.capture();
        }
    }

    public void reset() {
        this.startTimeNanos = 0L;
        this.endTimeNanos = 0L;
        this.completed = false;
        this.flaggedAsSlow = false;
        this.systemSnapshot = null;
        this.root.reset();
    }

    public long getFrameNumber() {
        return this.frameNumber;
    }

    public ProfilerSection getRoot() {
        return this.root;
    }

    public long getDurationNanos() {
        return this.endTimeNanos - this.startTimeNanos;
    }

    public double getDurationMillis() {
        return getDurationNanos() / NANOS_TO_MILLIS;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean isFlaggedAsSlow() {
        return this.flaggedAsSlow;
    }

    public SystemSnapshot getSystemSnapshot() {
        return this.systemSnapshot;
    }

    public ProfilerSection findSlowestSection(double thresholdMillis) {
        return findSlowestRecursive(this.root, thresholdMillis);
    }

    private ProfilerSection findSlowestRecursive(ProfilerSection section, double thresholdMillis) {
        ProfilerSection slowest = null;
        double slowestTime = thresholdMillis;
        for (int i = 0; i < section.getChildCount(); i++) {
            ProfilerSection child = section.getChildren()[i];
            if (child.getSelfTimeMillis() > slowestTime) {
                slowest = child;
                slowestTime = child.getSelfTimeMillis();
            }
            ProfilerSection childSlowest = findSlowestRecursive(child, slowestTime);
            if (childSlowest != null && childSlowest.getSelfTimeMillis() > slowestTime) {
                slowest = childSlowest;
                slowestTime = childSlowest.getSelfTimeMillis();
            }
        }
        return slowest;
    }

    public String buildCallTree(double thresholdMillis) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Frame #%d - %.2fms", Long.valueOf(this.frameNumber), Double.valueOf(getDurationMillis())));
        if (this.flaggedAsSlow) {
            builder.append(SLOW_FRAME_MARKER);
        }
        builder.append("\n");
        buildCallTreeRecursive(builder, this.root, thresholdMillis, "");
        return builder.toString();
    }

    private void buildCallTreeRecursive(StringBuilder builder, ProfilerSection section, double thresholdMillis, String indent) {
        int i = 0;
        while (i < section.getChildCount()) {
            ProfilerSection child = section.getChildren()[i];
            if (child.getDurationMillis() >= thresholdMillis) {
                boolean isLast = i == section.getChildCount() - 1;
                String prefix = isLast ? TREE_BRANCH_LAST : TREE_BRANCH;
                String childIndent = indent + (isLast ? TREE_INDENT_LAST : TREE_INDENT);
                builder.append(indent).append(prefix).append(child.getName());
                int callCount = child.getCallCount();
                if (callCount > 1) {
                    builder.append(String.format(" - %.2fms total (x%d calls, avg: %.2fms, min: %.2fms, max: %.2fms)", Double.valueOf(child.getDurationMillis()), Integer.valueOf(callCount), Double.valueOf(child.getAvgCallTimeMillis()), Double.valueOf(child.getMinCallTimeMillis()), Double.valueOf(child.getMaxCallTimeMillis())));
                } else {
                    builder.append(String.format(" - %.2fms (self: %.2fms, %.1f%%)", Double.valueOf(child.getDurationMillis()), Double.valueOf(child.getSelfTimeMillis()), Double.valueOf(child.getPercentageOfParent())));
                }
                builder.append("\n");
                buildCallTreeRecursive(builder, child, thresholdMillis, childIndent);
            }
            i++;
        }
    }
}
