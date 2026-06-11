package net.minecraft.server.level;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ColumnPos.class */
public final class ColumnPos extends Record {
    private final int x;
    private final int z;
    private static final long COORD_BITS = 32;
    private static final long COORD_MASK = 4294967295L;

    public ColumnPos(int $$0, int $$1) {
        this.x = $$0;
        this.z = $$1;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ColumnPos.class, Object.class), ColumnPos.class, "x;z", "FIELD:Lnet/minecraft/server/level/ColumnPos;->x:I", "FIELD:Lnet/minecraft/server/level/ColumnPos;->z:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int x() {
        return this.x;
    }

    public int z() {
        return this.z;
    }

    public ChunkPos toChunkPos() {
        return new ChunkPos(SectionPos.blockToSectionCoord(this.x), SectionPos.blockToSectionCoord(this.z));
    }

    public long toLong() {
        return asLong(this.x, this.z);
    }

    public static long asLong(int $$0, int $$1) {
        return (((long) $$0) & COORD_MASK) | ((((long) $$1) & COORD_MASK) << COORD_BITS);
    }

    public static int getX(long $$0) {
        return (int) ($$0 & COORD_MASK);
    }

    public static int getZ(long $$0) {
        return (int) (($$0 >>> COORD_BITS) & COORD_MASK);
    }

    @Override // java.lang.Record
    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    @Override // java.lang.Record
    public int hashCode() {
        return ChunkPos.hash(this.x, this.z);
    }
}
