package net.minecraft.util.profiling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ActiveProfiler.class */
public class ActiveProfiler implements ProfileCollector {
    private static final long WARNING_TIME_NANOS = Duration.ofMillis(100).toNanos();
    private static final Logger LOGGER = LogUtils.getLogger();
    private final IntSupplier getTickTime;
    private final LongSupplier getRealTime;
    private final long startTimeNano;
    private final int startTimeTicks;
    private boolean started;
    private PathEntry currentEntry;
    private final BooleanSupplier suppressWarnings;
    private final List<String> paths = Lists.newArrayList();
    private final LongList startTimes = new LongArrayList();
    private final Map<String, PathEntry> entries = Maps.newHashMap();
    private String path = "";
    private final Set<Pair<String, MetricCategory>> chartedPaths = new ObjectArraySet();

    public ActiveProfiler(LongSupplier $$0, IntSupplier $$1, BooleanSupplier $$2) {
        this.startTimeNano = $$0.getAsLong();
        this.getRealTime = $$0;
        this.startTimeTicks = $$1.getAsInt();
        this.getTickTime = $$1;
        this.suppressWarnings = $$2;
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void startTick() {
        if (this.started) {
            LOGGER.error("Profiler tick already started - missing endTick()?");
            return;
        }
        this.started = true;
        this.path = "";
        this.paths.clear();
        push("root");
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void endTick() {
        if (!this.started) {
            LOGGER.error("Profiler tick already ended - missing startTick()?");
            return;
        }
        pop();
        this.started = false;
        if (!this.path.isEmpty()) {
            LOGGER.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", LogUtils.defer(() -> {
                return ProfileResults.demanglePath(this.path);
            }));
        }
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void push(String $$0) {
        if (!this.started) {
            LOGGER.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", $$0);
            return;
        }
        if (!this.path.isEmpty()) {
            this.path += "\u001e";
        }
        this.path += $$0;
        this.paths.add(this.path);
        this.startTimes.add(Util.getNanos());
        this.currentEntry = null;
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void push(Supplier<String> $$0) {
        push($$0.get());
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void markForCharting(MetricCategory $$0) {
        this.chartedPaths.add(Pair.of(this.path, $$0));
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void pop() {
        if (!this.started) {
            LOGGER.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
            return;
        }
        if (this.startTimes.isEmpty()) {
            LOGGER.error("Tried to pop one too many times! Mismatched push() and pop()?");
            return;
        }
        long $$0 = Util.getNanos();
        long $$1 = this.startTimes.removeLong(this.startTimes.size() - 1);
        this.paths.removeLast();
        long $$2 = $$0 - $$1;
        PathEntry $$3 = getCurrentEntry();
        $$3.accumulatedDuration += $$2;
        $$3.count++;
        $$3.maxDuration = Math.max($$3.maxDuration, $$2);
        $$3.minDuration = Math.min($$3.minDuration, $$2);
        if ($$2 > WARNING_TIME_NANOS && !this.suppressWarnings.getAsBoolean()) {
            LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", LogUtils.defer(() -> {
                return ProfileResults.demanglePath(this.path);
            }), LogUtils.defer(() -> {
                return Double.valueOf($$2 / 1000000.0d);
            }));
        }
        this.path = this.paths.isEmpty() ? "" : (String) this.paths.getLast();
        this.currentEntry = null;
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void popPush(String $$0) {
        pop();
        push($$0);
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void popPush(Supplier<String> $$0) {
        pop();
        push($$0);
    }

    private PathEntry getCurrentEntry() {
        if (this.currentEntry == null) {
            this.currentEntry = this.entries.computeIfAbsent(this.path, $$0 -> {
                return new PathEntry();
            });
        }
        return this.currentEntry;
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void incrementCounter(String $$0, int $$1) {
        getCurrentEntry().counters.addTo($$0, $$1);
    }

    @Override // net.minecraft.util.profiling.ProfilerFiller
    public void incrementCounter(Supplier<String> $$0, int $$1) {
        getCurrentEntry().counters.addTo($$0.get(), $$1);
    }

    @Override // net.minecraft.util.profiling.ProfileCollector
    public ProfileResults getResults() {
        return new FilledProfileResults(this.entries, this.startTimeNano, this.startTimeTicks, this.getRealTime.getAsLong(), this.getTickTime.getAsInt());
    }

    @Override // net.minecraft.util.profiling.ProfileCollector
    public PathEntry getEntry(String $$0) {
        return this.entries.get($$0);
    }

    @Override // net.minecraft.util.profiling.ProfileCollector
    public Set<Pair<String, MetricCategory>> getChartedPaths() {
        return this.chartedPaths;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ActiveProfiler$PathEntry.class */
    public static class PathEntry implements ProfilerPathEntry {
        long accumulatedDuration;
        long count;
        long maxDuration = Long.MIN_VALUE;
        long minDuration = DynamicGraphMinFixedPoint.SOURCE;
        final Object2LongOpenHashMap<String> counters = new Object2LongOpenHashMap<>();

        @Override // net.minecraft.util.profiling.ProfilerPathEntry
        public long getDuration() {
            return this.accumulatedDuration;
        }

        @Override // net.minecraft.util.profiling.ProfilerPathEntry
        public long getMaxDuration() {
            return this.maxDuration;
        }

        @Override // net.minecraft.util.profiling.ProfilerPathEntry
        public long getCount() {
            return this.count;
        }

        @Override // net.minecraft.util.profiling.ProfilerPathEntry
        public Object2LongMap<String> getCounters() {
            return Object2LongMaps.unmodifiable(this.counters);
        }
    }
}
