package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/placement/StructurePlacement.class */
public abstract class StructurePlacement {
    public static final Codec<StructurePlacement> CODEC = BuiltInRegistries.STRUCTURE_PLACEMENT.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    private static final int HIGHLY_ARBITRARY_RANDOM_SALT = 10387320;
    private final Vec3i locateOffset;
    private final FrequencyReductionMethod frequencyReductionMethod;
    private final float frequency;
    private final int salt;
    private final Optional<ExclusionZone> exclusionZone;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/placement/StructurePlacement$FrequencyReducer.class */
    @FunctionalInterface
    public interface FrequencyReducer {
        boolean shouldGenerate(long j, int i, int i2, int i3, float f);
    }

    protected abstract boolean isPlacementChunk(ChunkGeneratorStructureState chunkGeneratorStructureState, int i, int i2);

    public abstract StructurePlacementType<?> type();

    protected static <S extends StructurePlacement> Products.P5<RecordCodecBuilder.Mu<S>, Vec3i, FrequencyReductionMethod, Float, Integer, Optional<ExclusionZone>> placementCodec(RecordCodecBuilder.Instance<S> $$0) {
        return $$0.group(Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter((v0) -> {
            return v0.locateOffset();
        }), FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter((v0) -> {
            return v0.frequencyReductionMethod();
        }), Codec.floatRange(0.0f, 1.0f).optionalFieldOf("frequency", Float.valueOf(1.0f)).forGetter((v0) -> {
            return v0.frequency();
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter((v0) -> {
            return v0.salt();
        }), ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter((v0) -> {
            return v0.exclusionZone();
        }));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone.class */
    @Deprecated
    public static final class ExclusionZone extends Record {
        private final Holder<StructureSet> otherSet;
        private final int chunkCount;
        public static final Codec<ExclusionZone> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(RegistryFileCodec.create(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC, false).fieldOf("other_set").forGetter((v0) -> {
                return v0.otherSet();
            }), Codec.intRange(1, 16).fieldOf("chunk_count").forGetter((v0) -> {
                return v0.chunkCount();
            })).apply($$0, (v1, v2) -> {
                return new ExclusionZone(v1, v2);
            });
        });

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ExclusionZone.class), ExclusionZone.class, "otherSet;chunkCount", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->otherSet:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->chunkCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ExclusionZone.class), ExclusionZone.class, "otherSet;chunkCount", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->otherSet:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->chunkCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ExclusionZone.class, Object.class), ExclusionZone.class, "otherSet;chunkCount", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->otherSet:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;->chunkCount:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<StructureSet> otherSet() {
            return this.otherSet;
        }

        public int chunkCount() {
            return this.chunkCount;
        }

        public ExclusionZone(Holder<StructureSet> $$0, int $$1) {
            this.otherSet = $$0;
            this.chunkCount = $$1;
        }

        boolean isPlacementForbidden(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
            return $$0.hasStructureChunkInRange(this.otherSet, $$1, $$2, this.chunkCount);
        }
    }

    protected StructurePlacement(Vec3i $$0, FrequencyReductionMethod $$1, float $$2, int $$3, Optional<ExclusionZone> $$4) {
        this.locateOffset = $$0;
        this.frequencyReductionMethod = $$1;
        this.frequency = $$2;
        this.salt = $$3;
        this.exclusionZone = $$4;
    }

    protected Vec3i locateOffset() {
        return this.locateOffset;
    }

    protected FrequencyReductionMethod frequencyReductionMethod() {
        return this.frequencyReductionMethod;
    }

    protected float frequency() {
        return this.frequency;
    }

    protected int salt() {
        return this.salt;
    }

    protected Optional<ExclusionZone> exclusionZone() {
        return this.exclusionZone;
    }

    public boolean isStructureChunk(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
        return isPlacementChunk($$0, $$1, $$2) && applyAdditionalChunkRestrictions($$1, $$2, $$0.getLevelSeed()) && applyInteractionsWithOtherStructures($$0, $$1, $$2);
    }

    public boolean applyAdditionalChunkRestrictions(int $$0, int $$1, long $$2) {
        if (this.frequency < 1.0f && !this.frequencyReductionMethod.shouldGenerate($$2, this.salt, $$0, $$1, this.frequency)) {
            return false;
        }
        return true;
    }

    public boolean applyInteractionsWithOtherStructures(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
        if (this.exclusionZone.isPresent() && this.exclusionZone.get().isPlacementForbidden($$0, $$1, $$2)) {
            return false;
        }
        return true;
    }

    public BlockPos getLocatePos(ChunkPos $$0) {
        return new BlockPos($$0.getMinBlockX(), 0, $$0.getMinBlockZ()).offset(locateOffset());
    }

    private static boolean probabilityReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.setLargeFeatureWithSalt($$0, $$1, $$2, $$3);
        return $$5.nextFloat() < $$4;
    }

    private static boolean legacyProbabilityReducerWithDouble(long $$0, int $$1, int $$2, int $$3, float $$4) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.setLargeFeatureSeed($$0, $$2, $$3);
        return $$5.nextDouble() < ((double) $$4);
    }

    private static boolean legacyArbitrarySaltProbabilityReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.setLargeFeatureWithSalt($$0, $$2, $$3, HIGHLY_ARBITRARY_RANDOM_SALT);
        return $$5.nextFloat() < $$4;
    }

    private static boolean legacyPillagerOutpostReducer(long $$0, int $$1, int $$2, int $$3, float $$4) {
        int $$5 = $$2 >> 4;
        int $$6 = $$3 >> 4;
        WorldgenRandom $$7 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$7.setSeed(((long) ($$5 ^ ($$6 << 4))) ^ $$0);
        $$7.nextInt();
        return $$7.nextInt((int) (1.0f / $$4)) == 0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/placement/StructurePlacement$FrequencyReductionMethod.class */
    public enum FrequencyReductionMethod implements StringRepresentable {
        DEFAULT(GameTestEnvironments.DEFAULT, StructurePlacement::probabilityReducer),
        LEGACY_TYPE_1("legacy_type_1", StructurePlacement::legacyPillagerOutpostReducer),
        LEGACY_TYPE_2("legacy_type_2", StructurePlacement::legacyArbitrarySaltProbabilityReducer),
        LEGACY_TYPE_3("legacy_type_3", StructurePlacement::legacyProbabilityReducerWithDouble);

        public static final Codec<FrequencyReductionMethod> CODEC = StringRepresentable.fromEnum(FrequencyReductionMethod::values);
        private final String name;
        private final FrequencyReducer reducer;

        FrequencyReductionMethod(String $$0, FrequencyReducer $$1) {
            this.name = $$0;
            this.reducer = $$1;
        }

        public boolean shouldGenerate(long $$0, int $$1, int $$2, int $$3, float $$4) {
            return this.reducer.shouldGenerate($$0, $$1, $$2, $$3, $$4);
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}
