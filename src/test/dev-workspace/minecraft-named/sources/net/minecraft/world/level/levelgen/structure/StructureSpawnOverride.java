package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.MobSpawnSettings;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/StructureSpawnOverride.class */
public final class StructureSpawnOverride extends Record {
    private final BoundingBoxType boundingBox;
    private final WeightedList<MobSpawnSettings.SpawnerData> spawns;
    public static final Codec<StructureSpawnOverride> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BoundingBoxType.CODEC.fieldOf("bounding_box").forGetter((v0) -> {
            return v0.boundingBox();
        }), WeightedList.codec(MobSpawnSettings.SpawnerData.CODEC).fieldOf("spawns").forGetter((v0) -> {
            return v0.spawns();
        })).apply($$0, StructureSpawnOverride::new);
    });

    public StructureSpawnOverride(BoundingBoxType $$0, WeightedList<MobSpawnSettings.SpawnerData> $$1) {
        this.boundingBox = $$0;
        this.spawns = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StructureSpawnOverride.class), StructureSpawnOverride.class, "boundingBox;spawns", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride$BoundingBoxType;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->spawns:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StructureSpawnOverride.class), StructureSpawnOverride.class, "boundingBox;spawns", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride$BoundingBoxType;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->spawns:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StructureSpawnOverride.class, Object.class), StructureSpawnOverride.class, "boundingBox;spawns", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->boundingBox:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride$BoundingBoxType;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;->spawns:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BoundingBoxType boundingBox() {
        return this.boundingBox;
    }

    public WeightedList<MobSpawnSettings.SpawnerData> spawns() {
        return this.spawns;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/StructureSpawnOverride$BoundingBoxType.class */
    public enum BoundingBoxType implements StringRepresentable {
        PIECE("piece"),
        STRUCTURE("full");

        public static final Codec<BoundingBoxType> CODEC = StringRepresentable.fromEnum(BoundingBoxType::values);
        private final String id;

        BoundingBoxType(String $$0) {
            this.id = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.id;
        }
    }
}
