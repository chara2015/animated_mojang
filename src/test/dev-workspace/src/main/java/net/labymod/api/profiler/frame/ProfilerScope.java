package net.labymod.api.profiler.frame;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/ProfilerScope.class */
public final class ProfilerScope implements AutoCloseable {
    private static final ProfilerScope DISABLED = new ProfilerScope(false);
    private final boolean active;

    private ProfilerScope(boolean active) {
        this.active = active;
    }

    public static ProfilerScope of(String name) {
        if (!FrameProfiler.isEnabled()) {
            return DISABLED;
        }
        FrameProfiler.push(name);
        return new ProfilerScope(true);
    }

    public static ProfilerScope ofIf(String name, boolean condition) {
        if (!condition || !FrameProfiler.isEnabled()) {
            return DISABLED;
        }
        FrameProfiler.push(name);
        return new ProfilerScope(true);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.active) {
            FrameProfiler.pop();
        }
    }
}
