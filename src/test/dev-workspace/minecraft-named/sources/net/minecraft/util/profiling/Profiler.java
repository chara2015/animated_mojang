package net.minecraft.util.profiling;

import com.mojang.jtracy.TracyClient;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/Profiler.class */
public final class Profiler {
    private static final ThreadLocal<TracyZoneFiller> TRACY_FILLER = ThreadLocal.withInitial(TracyZoneFiller::new);
    private static final ThreadLocal<ProfilerFiller> ACTIVE = new ThreadLocal<>();
    private static final AtomicInteger ACTIVE_COUNT = new AtomicInteger();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/Profiler$Scope.class */
    public interface Scope extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();
    }

    private Profiler() {
    }

    public static Scope use(ProfilerFiller $$0) {
        startUsing($$0);
        return Profiler::stopUsing;
    }

    private static void startUsing(ProfilerFiller $$0) {
        if (ACTIVE.get() != null) {
            throw new IllegalStateException("Profiler is already active");
        }
        ProfilerFiller $$1 = decorateFiller($$0);
        ACTIVE.set($$1);
        ACTIVE_COUNT.incrementAndGet();
        $$1.startTick();
    }

    private static void stopUsing() {
        ProfilerFiller $$0 = ACTIVE.get();
        if ($$0 == null) {
            throw new IllegalStateException("Profiler was not active");
        }
        ACTIVE.remove();
        ACTIVE_COUNT.decrementAndGet();
        $$0.endTick();
    }

    private static ProfilerFiller decorateFiller(ProfilerFiller $$0) {
        return ProfilerFiller.combine(getDefaultFiller(), $$0);
    }

    public static ProfilerFiller get() {
        if (ACTIVE_COUNT.get() == 0) {
            return getDefaultFiller();
        }
        return (ProfilerFiller) Objects.requireNonNullElseGet(ACTIVE.get(), Profiler::getDefaultFiller);
    }

    private static ProfilerFiller getDefaultFiller() {
        if (TracyClient.isAvailable()) {
            return TRACY_FILLER.get();
        }
        return InactiveProfiler.INSTANCE;
    }
}
