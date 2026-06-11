package net.minecraft.util.profiling.jfr.stats;

import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/FileIOStat.class */
public final class FileIOStat extends Record {
    private final Duration duration;
    private final String path;
    private final long bytes;

    public FileIOStat(Duration $$0, String $$1, long $$2) {
        this.duration = $$0;
        this.path = $$1;
        this.bytes = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FileIOStat.class), FileIOStat.class, "duration;path;bytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->path:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->bytes:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FileIOStat.class), FileIOStat.class, "duration;path;bytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->path:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->bytes:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FileIOStat.class, Object.class), FileIOStat.class, "duration;path;bytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->path:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;->bytes:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Duration duration() {
        return this.duration;
    }

    public String path() {
        return this.path;
    }

    public long bytes() {
        return this.bytes;
    }

    public static Summary summary(Duration $$0, List<FileIOStat> $$1) {
        long $$2 = $$1.stream().mapToLong($$02 -> {
            return $$02.bytes;
        }).sum();
        return new Summary($$2, $$2 / $$0.getSeconds(), $$1.size(), ((double) $$1.size()) / $$0.getSeconds(), (Duration) $$1.stream().map((v0) -> {
            return v0.duration();
        }).reduce(Duration.ZERO, (v0, v1) -> {
            return v0.plus(v1);
        }), ((Map) $$1.stream().filter($$03 -> {
            return $$03.path != null;
        }).collect(Collectors.groupingBy($$04 -> {
            return $$04.path;
        }, Collectors.summingLong($$05 -> {
            return $$05.bytes;
        })))).entrySet().stream().sorted(Map.Entry.comparingByValue().reversed()).map($$06 -> {
            return Pair.of((String) $$06.getKey(), (Long) $$06.getValue());
        }).limit(10L).toList());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/FileIOStat$Summary.class */
    public static final class Summary extends Record {
        private final long totalBytes;
        private final double bytesPerSecond;
        private final long counts;
        private final double countsPerSecond;
        private final Duration timeSpentInIO;
        private final List<Pair<String, Long>> topTenContributorsByTotalBytes;

        public Summary(long $$0, double $$1, long $$2, double $$3, Duration $$4, List<Pair<String, Long>> $$5) {
            this.totalBytes = $$0;
            this.bytesPerSecond = $$1;
            this.counts = $$2;
            this.countsPerSecond = $$3;
            this.timeSpentInIO = $$4;
            this.topTenContributorsByTotalBytes = $$5;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Summary.class), Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->totalBytes:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->bytesPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->counts:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->countsPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->timeSpentInIO:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->topTenContributorsByTotalBytes:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Summary.class), Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->totalBytes:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->bytesPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->counts:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->countsPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->timeSpentInIO:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->topTenContributorsByTotalBytes:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Summary.class, Object.class), Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->totalBytes:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->bytesPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->counts:J", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->countsPerSecond:D", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->timeSpentInIO:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;->topTenContributorsByTotalBytes:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public long totalBytes() {
            return this.totalBytes;
        }

        public double bytesPerSecond() {
            return this.bytesPerSecond;
        }

        public long counts() {
            return this.counts;
        }

        public double countsPerSecond() {
            return this.countsPerSecond;
        }

        public Duration timeSpentInIO() {
            return this.timeSpentInIO;
        }

        public List<Pair<String, Long>> topTenContributorsByTotalBytes() {
            return this.topTenContributorsByTotalBytes;
        }
    }
}
