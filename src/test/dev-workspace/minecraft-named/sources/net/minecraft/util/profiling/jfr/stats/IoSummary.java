package net.minecraft.util.profiling.jfr.stats;

import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/IoSummary.class */
public final class IoSummary<T> {
    private final CountAndSize totalCountAndSize;
    private final List<Pair<T, CountAndSize>> largestSizeContributors;
    private final Duration recordingDuration;

    public IoSummary(Duration $$0, List<Pair<T, CountAndSize>> $$1) {
        this.recordingDuration = $$0;
        this.totalCountAndSize = (CountAndSize) $$1.stream().map((v0) -> {
            return v0.getSecond();
        }).reduce(new CountAndSize(0L, 0L), (v0, v1) -> {
            return v0.add(v1);
        });
        this.largestSizeContributors = $$1.stream().sorted(Comparator.comparing((v0) -> {
            return v0.getSecond();
        }, CountAndSize.SIZE_THEN_COUNT)).limit(10L).toList();
    }

    public double getCountsPerSecond() {
        return this.totalCountAndSize.totalCount / this.recordingDuration.getSeconds();
    }

    public double getSizePerSecond() {
        return this.totalCountAndSize.totalSize / this.recordingDuration.getSeconds();
    }

    public long getTotalCount() {
        return this.totalCountAndSize.totalCount;
    }

    public long getTotalSize() {
        return this.totalCountAndSize.totalSize;
    }

    public List<Pair<T, CountAndSize>> largestSizeContributors() {
        return this.largestSizeContributors;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize.class */
    public static final class CountAndSize extends Record {
        private final long totalCount;
        private final long totalSize;
        static final Comparator<CountAndSize> SIZE_THEN_COUNT = Comparator.comparing((v0) -> {
            return v0.totalSize();
        }).thenComparing((v0) -> {
            return v0.totalCount();
        }).reversed();

        public CountAndSize(long $$0, long $$1) {
            this.totalCount = $$0;
            this.totalSize = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CountAndSize.class), CountAndSize.class, "totalCount;totalSize", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalCount:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalSize:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CountAndSize.class), CountAndSize.class, "totalCount;totalSize", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalCount:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalSize:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CountAndSize.class, Object.class), CountAndSize.class, "totalCount;totalSize", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalCount:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/IoSummary$CountAndSize;->totalSize:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long totalCount() {
            return this.totalCount;
        }

        public long totalSize() {
            return this.totalSize;
        }

        CountAndSize add(CountAndSize $$0) {
            return new CountAndSize(this.totalCount + $$0.totalCount, this.totalSize + $$0.totalSize);
        }

        public float averageSize() {
            return this.totalSize / this.totalCount;
        }
    }
}
