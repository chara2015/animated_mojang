package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/NetherForestVegetationConfig.class */
public class NetherForestVegetationConfig extends BlockPileConfiguration {
    public static final Codec<NetherForestVegetationConfig> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockStateProvider.CODEC.fieldOf("state_provider").forGetter($$0 -> {
            return $$0.stateProvider;
        }), ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter($$02 -> {
            return Integer.valueOf($$02.spreadWidth);
        }), ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter($$03 -> {
            return Integer.valueOf($$03.spreadHeight);
        })).apply($$0, (v1, v2, v3) -> {
            return new NetherForestVegetationConfig(v1, v2, v3);
        });
    });
    public final int spreadWidth;
    public final int spreadHeight;

    public NetherForestVegetationConfig(BlockStateProvider $$0, int $$1, int $$2) {
        super($$0);
        this.spreadWidth = $$1;
        this.spreadHeight = $$2;
    }
}
