package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/ChunkIdentification.class */
public final class ChunkIdentification extends Record {
    private final String level;
    private final String dimension;
    private final int x;
    private final int z;

    public ChunkIdentification(String $$0, String $$1, int $$2, int $$3) {
        this.level = $$0;
        this.dimension = $$1;
        this.x = $$2;
        this.z = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChunkIdentification.class), ChunkIdentification.class, "level;dimension;x;z", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->dimension:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->x:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->z:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChunkIdentification.class), ChunkIdentification.class, "level;dimension;x;z", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->dimension:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->x:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->z:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChunkIdentification.class, Object.class), ChunkIdentification.class, "level;dimension;x;z", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->level:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->dimension:Ljava/lang/String;", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->x:I", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/ChunkIdentification;->z:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String level() {
        return this.level;
    }

    public String dimension() {
        return this.dimension;
    }

    public int x() {
        return this.x;
    }

    public int z() {
        return this.z;
    }

    public static ChunkIdentification from(RecordedEvent $$0) {
        return new ChunkIdentification($$0.getString("level"), $$0.getString(ChunkRegionIoEvent.Fields.DIMENSION), $$0.getInt("chunkPosX"), $$0.getInt("chunkPosZ"));
    }
}
