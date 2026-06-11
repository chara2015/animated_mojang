package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.util.profiling.jfr.event.StructureGenerationEvent;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/StructureGenStat.class */
public final class StructureGenStat extends Record implements TimedStat {
    private final Duration duration;
    private final ChunkPos chunkPos;
    private final String structureName;
    private final String level;
    private final boolean success;

    public StructureGenStat(Duration $$0, ChunkPos $$1, String $$2, String $$3, boolean $$4) {
        this.duration = $$0;
        this.chunkPos = $$1;
        this.structureName = $$2;
        this.level = $$3;
        this.success = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StructureGenStat.class), StructureGenStat.class, "duration;chunkPos;structureName;level;success", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->structureName:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->success:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StructureGenStat.class), StructureGenStat.class, "duration;chunkPos;structureName;level;success", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->structureName:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->success:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StructureGenStat.class, Object.class), StructureGenStat.class, "duration;chunkPos;structureName;level;success", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->duration:Ljava/time/Duration;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->structureName:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/StructureGenStat;->success:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.util.profiling.jfr.stats.TimedStat
    public Duration duration() {
        return this.duration;
    }

    public ChunkPos chunkPos() {
        return this.chunkPos;
    }

    public String structureName() {
        return this.structureName;
    }

    public String level() {
        return this.level;
    }

    public boolean success() {
        return this.success;
    }

    public static StructureGenStat from(RecordedEvent $$0) {
        return new StructureGenStat($$0.getDuration(), new ChunkPos($$0.getInt("chunkPosX"), $$0.getInt("chunkPosX")), $$0.getString("structure"), $$0.getString("level"), $$0.getBoolean(StructureGenerationEvent.Fields.SUCCESS));
    }
}
