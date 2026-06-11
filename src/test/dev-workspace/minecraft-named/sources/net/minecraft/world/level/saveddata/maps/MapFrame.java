package net.minecraft.world.level.saveddata.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/saveddata/maps/MapFrame.class */
public final class MapFrame extends Record {
    private final BlockPos pos;
    private final int rotation;
    private final int entityId;
    public static final Codec<MapFrame> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockPos.CODEC.fieldOf("pos").forGetter((v0) -> {
            return v0.pos();
        }), Codec.INT.fieldOf("rotation").forGetter((v0) -> {
            return v0.rotation();
        }), Codec.INT.fieldOf("entity_id").forGetter((v0) -> {
            return v0.entityId();
        })).apply($$0, (v1, v2, v3) -> {
            return new MapFrame(v1, v2, v3);
        });
    });

    public MapFrame(BlockPos $$0, int $$1, int $$2) {
        this.pos = $$0;
        this.rotation = $$1;
        this.entityId = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MapFrame.class), MapFrame.class, "pos;rotation;entityId", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->rotation:I", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->entityId:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MapFrame.class), MapFrame.class, "pos;rotation;entityId", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->rotation:I", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->entityId:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MapFrame.class, Object.class), MapFrame.class, "pos;rotation;entityId", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->rotation:I", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapFrame;->entityId:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public int rotation() {
        return this.rotation;
    }

    public int entityId() {
        return this.entityId;
    }

    public String getId() {
        return frameId(this.pos);
    }

    public static String frameId(BlockPos $$0) {
        return "frame-" + $$0.getX() + "," + $$0.getY() + "," + $$0.getZ();
    }
}
