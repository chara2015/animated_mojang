package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/ChunkGenStat.class */
public final class ChunkGenStat extends Record implements TimedStat {
    private final Duration duration;
    private final ChunkPos chunkPos;
    private final ColumnPos worldPos;
    private final ChunkStatus status;
    private final String level;

    public ChunkGenStat(Duration $$0, ChunkPos $$1, ColumnPos $$2, ChunkStatus $$3, String $$4) {
        this.duration = $$0;
        this.chunkPos = $$1;
        this.worldPos = $$2;
        this.status = $$3;
        this.level = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChunkGenStat.class), ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->worldPos:Lnet/minecraft/server/level/ColumnPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->status:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->level:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChunkGenStat.class), ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->worldPos:Lnet/minecraft/server/level/ColumnPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->status:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->level:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChunkGenStat.class, Object.class), ChunkGenStat.class, "duration;chunkPos;worldPos;status;level", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->worldPos:Lnet/minecraft/server/level/ColumnPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->status:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;->level:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.util.profiling.jfr.stats.TimedStat
    public Duration duration() {
        return this.duration;
    }

    public ChunkPos chunkPos() {
        return this.chunkPos;
    }

    public ColumnPos worldPos() {
        return this.worldPos;
    }

    public ChunkStatus status() {
        return this.status;
    }

    public String level() {
        return this.level;
    }

    public static ChunkGenStat from(RecordedEvent $$0) {
        return new ChunkGenStat($$0.getDuration(), new ChunkPos($$0.getInt("chunkPosX"), $$0.getInt("chunkPosX")), new ColumnPos($$0.getInt(ChunkGenerationEvent.Fields.WORLD_POS_X), $$0.getInt(ChunkGenerationEvent.Fields.WORLD_POS_Z)), ChunkStatus.byName($$0.getString(ChunkGenerationEvent.Fields.STATUS)), $$0.getString("level"));
    }
}
