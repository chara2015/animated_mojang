package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/placement/RandomSpreadStructurePlacement.class */
public class RandomSpreadStructurePlacement extends StructurePlacement {
    public static final MapCodec<RandomSpreadStructurePlacement> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return placementCodec($$0).and($$0.group(Codec.intRange(0, 4096).fieldOf("spacing").forGetter((v0) -> {
            return v0.spacing();
        }), Codec.intRange(0, 4096).fieldOf("separation").forGetter((v0) -> {
            return v0.separation();
        }), RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter((v0) -> {
            return v0.spreadType();
        }))).apply($$0, (v1, v2, v3, v4, v5, v6, v7, v8) -> {
            return new RandomSpreadStructurePlacement(v1, v2, v3, v4, v5, v6, v7, v8);
        });
    }).validate(RandomSpreadStructurePlacement::validate);
    private final int spacing;
    private final int separation;
    private final RandomSpreadType spreadType;

    private static DataResult<RandomSpreadStructurePlacement> validate(RandomSpreadStructurePlacement $$0) {
        if ($$0.spacing <= $$0.separation) {
            return DataResult.error(() -> {
                return "Spacing has to be larger than separation";
            });
        }
        return DataResult.success($$0);
    }

    public RandomSpreadStructurePlacement(Vec3i $$0, StructurePlacement.FrequencyReductionMethod $$1, float $$2, int $$3, Optional<StructurePlacement.ExclusionZone> $$4, int $$5, int $$6, RandomSpreadType $$7) {
        super($$0, $$1, $$2, $$3, $$4);
        this.spacing = $$5;
        this.separation = $$6;
        this.spreadType = $$7;
    }

    public RandomSpreadStructurePlacement(int $$0, int $$1, RandomSpreadType $$2, int $$3) {
        this(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, $$3, Optional.empty(), $$0, $$1, $$2);
    }

    public int spacing() {
        return this.spacing;
    }

    public int separation() {
        return this.separation;
    }

    public RandomSpreadType spreadType() {
        return this.spreadType;
    }

    public ChunkPos getPotentialStructureChunk(long $$0, int $$1, int $$2) {
        int $$3 = Math.floorDiv($$1, this.spacing);
        int $$4 = Math.floorDiv($$2, this.spacing);
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.setLargeFeatureWithSalt($$0, $$3, $$4, salt());
        int $$6 = this.spacing - this.separation;
        int $$7 = this.spreadType.evaluate($$5, $$6);
        int $$8 = this.spreadType.evaluate($$5, $$6);
        return new ChunkPos(($$3 * this.spacing) + $$7, ($$4 * this.spacing) + $$8);
    }

    @Override // net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
    protected boolean isPlacementChunk(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
        ChunkPos $$3 = getPotentialStructureChunk($$0.getLevelSeed(), $$1, $$2);
        return $$3.x == $$1 && $$3.z == $$2;
    }

    @Override // net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
    public StructurePlacementType<?> type() {
        return StructurePlacementType.RANDOM_SPREAD;
    }
}
