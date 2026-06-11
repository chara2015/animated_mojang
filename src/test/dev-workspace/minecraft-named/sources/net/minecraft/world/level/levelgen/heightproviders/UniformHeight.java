package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/heightproviders/UniformHeight.class */
public class UniformHeight extends HeightProvider {
    public static final MapCodec<UniformHeight> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter($$0 -> {
            return $$0.minInclusive;
        }), VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter($$02 -> {
            return $$02.maxInclusive;
        })).apply($$0, UniformHeight::new);
    });
    private static final Logger LOGGER = LogUtils.getLogger();
    private final VerticalAnchor minInclusive;
    private final VerticalAnchor maxInclusive;
    private final LongSet warnedFor = new LongOpenHashSet();

    private UniformHeight(VerticalAnchor $$0, VerticalAnchor $$1) {
        this.minInclusive = $$0;
        this.maxInclusive = $$1;
    }

    public static UniformHeight of(VerticalAnchor $$0, VerticalAnchor $$1) {
        return new UniformHeight($$0, $$1);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public int sample(RandomSource $$0, WorldGenerationContext $$1) {
        int $$2 = this.minInclusive.resolveY($$1);
        int $$3 = this.maxInclusive.resolveY($$1);
        if ($$2 > $$3) {
            if (this.warnedFor.add((((long) $$2) << 32) | ((long) $$3))) {
                LOGGER.warn("Empty height range: {}", this);
            }
            return $$2;
        }
        return Mth.randomBetweenInclusive($$0, $$2, $$3);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public HeightProviderType<?> getType() {
        return HeightProviderType.UNIFORM;
    }

    public String toString() {
        return "[" + String.valueOf(this.minInclusive) + "-" + String.valueOf(this.maxInclusive) + "]";
    }
}
