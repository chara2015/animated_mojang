package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/heightproviders/TrapezoidHeight.class */
public class TrapezoidHeight extends HeightProvider {
    public static final MapCodec<TrapezoidHeight> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter($$0 -> {
            return $$0.minInclusive;
        }), VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter($$02 -> {
            return $$02.maxInclusive;
        }), Codec.INT.optionalFieldOf("plateau", 0).forGetter($$03 -> {
            return Integer.valueOf($$03.plateau);
        })).apply($$0, (v1, v2, v3) -> {
            return new TrapezoidHeight(v1, v2, v3);
        });
    });
    private static final Logger LOGGER = LogUtils.getLogger();
    private final VerticalAnchor minInclusive;
    private final VerticalAnchor maxInclusive;
    private final int plateau;

    private TrapezoidHeight(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
        this.minInclusive = $$0;
        this.maxInclusive = $$1;
        this.plateau = $$2;
    }

    public static TrapezoidHeight of(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
        return new TrapezoidHeight($$0, $$1, $$2);
    }

    public static TrapezoidHeight of(VerticalAnchor $$0, VerticalAnchor $$1) {
        return of($$0, $$1, 0);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public int sample(RandomSource $$0, WorldGenerationContext $$1) {
        int $$2 = this.minInclusive.resolveY($$1);
        int $$3 = this.maxInclusive.resolveY($$1);
        if ($$2 > $$3) {
            LOGGER.warn("Empty height range: {}", this);
            return $$2;
        }
        int $$4 = $$3 - $$2;
        if (this.plateau >= $$4) {
            return Mth.randomBetweenInclusive($$0, $$2, $$3);
        }
        int $$5 = ($$4 - this.plateau) / 2;
        int $$6 = $$4 - $$5;
        return $$2 + Mth.randomBetweenInclusive($$0, 0, $$6) + Mth.randomBetweenInclusive($$0, 0, $$5);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public HeightProviderType<?> getType() {
        return HeightProviderType.TRAPEZOID;
    }

    public String toString() {
        if (this.plateau == 0) {
            return "triangle (" + String.valueOf(this.minInclusive) + "-" + String.valueOf(this.maxInclusive) + ")";
        }
        return "trapezoid(" + this.plateau + ") in [" + String.valueOf(this.minInclusive) + "-" + String.valueOf(this.maxInclusive) + "]";
    }
}
