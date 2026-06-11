package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.profiling.jfr.Percentiles;
import net.minecraft.util.profiling.jfr.stats.TimedStat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/TimedStatSummary.class */
public final class TimedStatSummary<T extends TimedStat> extends Record {
    private final T fastest;
    private final T slowest;
    private final T secondSlowest;
    private final int count;
    private final Map<Integer, Double> percentilesNanos;
    private final Duration totalDuration;

    public TimedStatSummary(T $$0, T $$1, T $$2, int $$3, Map<Integer, Double> $$4, Duration $$5) {
        this.fastest = $$0;
        this.slowest = $$1;
        this.secondSlowest = $$2;
        this.count = $$3;
        this.percentilesNanos = $$4;
        this.totalDuration = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TimedStatSummary.class), TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->fastest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->slowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->secondSlowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->count:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->percentilesNanos:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->totalDuration:Ljava/time/Duration;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TimedStatSummary.class), TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->fastest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->slowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->secondSlowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->count:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->percentilesNanos:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->totalDuration:Ljava/time/Duration;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TimedStatSummary.class, Object.class), TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->fastest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->slowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->secondSlowest:Lnet/minecraft/util/profiling/jfr/stats/TimedStat;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->count:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->percentilesNanos:Ljava/util/Map;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;->totalDuration:Ljava/time/Duration;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T fastest() {
        return this.fastest;
    }

    public T slowest() {
        return this.slowest;
    }

    public T secondSlowest() {
        return this.secondSlowest;
    }

    public int count() {
        return this.count;
    }

    public Map<Integer, Double> percentilesNanos() {
        return this.percentilesNanos;
    }

    public Duration totalDuration() {
        return this.totalDuration;
    }

    public static <T extends TimedStat> Optional<TimedStatSummary<T>> summary(List<T> $$0) {
        if ($$0.isEmpty()) {
            return Optional.empty();
        }
        List<T> $$1 = $$0.stream().sorted(Comparator.comparing((v0) -> {
            return v0.duration();
        })).toList();
        Duration $$2 = (Duration) $$1.stream().map((v0) -> {
            return v0.duration();
        }).reduce((v0, v1) -> {
            return v0.plus(v1);
        }).orElse(Duration.ZERO);
        TimedStat timedStat = (TimedStat) $$1.getFirst();
        TimedStat timedStat2 = (TimedStat) $$1.getLast();
        T $$5 = $$1.size() > 1 ? $$1.get($$1.size() - 2) : null;
        int $$6 = $$1.size();
        Map<Integer, Double> $$7 = Percentiles.evaluate($$1.stream().mapToLong($$02 -> {
            return $$02.duration().toNanos();
        }).toArray());
        return Optional.of(new TimedStatSummary(timedStat, timedStat2, $$5, $$6, $$7, $$2));
    }
}
