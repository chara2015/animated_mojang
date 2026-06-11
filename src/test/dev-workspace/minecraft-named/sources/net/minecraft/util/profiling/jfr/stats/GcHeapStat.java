package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jdk.jfr.consumer.RecordedEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/GcHeapStat.class */
public final class GcHeapStat extends Record {
    private final Instant timestamp;
    private final long heapUsed;
    private final Timing timing;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/GcHeapStat$Timing.class */
    enum Timing {
        BEFORE_GC,
        AFTER_GC
    }

    public GcHeapStat(Instant $$0, long $$1, Timing $$2) {
        this.timestamp = $$0;
        this.heapUsed = $$1;
        this.timing = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GcHeapStat.class), GcHeapStat.class, "timestamp;heapUsed;timing", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timestamp:Ljava/time/Instant;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->heapUsed:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timing:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Timing;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GcHeapStat.class), GcHeapStat.class, "timestamp;heapUsed;timing", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timestamp:Ljava/time/Instant;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->heapUsed:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timing:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Timing;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GcHeapStat.class, Object.class), GcHeapStat.class, "timestamp;heapUsed;timing", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timestamp:Ljava/time/Instant;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->heapUsed:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;->timing:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Timing;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Instant timestamp() {
        return this.timestamp;
    }

    public long heapUsed() {
        return this.heapUsed;
    }

    public Timing timing() {
        return this.timing;
    }

    public static GcHeapStat from(RecordedEvent $$0) {
        Timing timing;
        Instant startTime = $$0.getStartTime();
        long j = $$0.getLong("heapUsed");
        if ($$0.getString("when").equalsIgnoreCase("before gc")) {
            timing = Timing.BEFORE_GC;
        } else {
            timing = Timing.AFTER_GC;
        }
        return new GcHeapStat(startTime, j, timing);
    }

    public static Summary summary(Duration $$0, List<GcHeapStat> $$1, Duration $$2, int $$3) {
        return new Summary($$0, $$2, $$3, calculateAllocationRatePerSecond($$1));
    }

    private static double calculateAllocationRatePerSecond(List<GcHeapStat> $$0) {
        long $$1 = 0;
        Map<Timing, List<GcHeapStat>> $$2 = (Map) $$0.stream().collect(Collectors.groupingBy($$02 -> {
            return $$02.timing;
        }));
        List<GcHeapStat> $$3 = $$2.get(Timing.BEFORE_GC);
        List<GcHeapStat> $$4 = $$2.get(Timing.AFTER_GC);
        for (int $$5 = 1; $$5 < $$3.size(); $$5++) {
            GcHeapStat $$6 = $$3.get($$5);
            GcHeapStat $$7 = $$4.get($$5 - 1);
            $$1 += $$6.heapUsed - $$7.heapUsed;
        }
        Duration $$8 = Duration.between($$0.get(1).timestamp, $$0.get($$0.size() - 1).timestamp);
        return $$1 / $$8.getSeconds();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary.class */
    public static final class Summary extends Record {
        private final Duration duration;
        private final Duration gcTotalDuration;
        private final int totalGCs;
        private final double allocationRateBytesPerSecond;

        public Summary(Duration $$0, Duration $$1, int $$2, double $$3) {
            this.duration = $$0;
            this.gcTotalDuration = $$1;
            this.totalGCs = $$2;
            this.allocationRateBytesPerSecond = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Summary.class), Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->gcTotalDuration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->totalGCs:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->allocationRateBytesPerSecond:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Summary.class), Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->gcTotalDuration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->totalGCs:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->allocationRateBytesPerSecond:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Summary.class, Object.class), Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->gcTotalDuration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->totalGCs:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;->allocationRateBytesPerSecond:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Duration duration() {
            return this.duration;
        }

        public Duration gcTotalDuration() {
            return this.gcTotalDuration;
        }

        public int totalGCs() {
            return this.totalGCs;
        }

        public double allocationRateBytesPerSecond() {
            return this.allocationRateBytesPerSecond;
        }

        public float gcOverHead() {
            return this.gcTotalDuration.toMillis() / this.duration.toMillis();
        }
    }
}
