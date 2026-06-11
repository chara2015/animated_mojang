package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/HugeFungusConfiguration.class */
public class HugeFungusConfiguration implements FeatureConfiguration {
    public static final Codec<HugeFungusConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockState.CODEC.fieldOf("valid_base_block").forGetter($$0 -> {
            return $$0.validBaseState;
        }), BlockState.CODEC.fieldOf("stem_state").forGetter($$02 -> {
            return $$02.stemState;
        }), BlockState.CODEC.fieldOf("hat_state").forGetter($$03 -> {
            return $$03.hatState;
        }), BlockState.CODEC.fieldOf("decor_state").forGetter($$04 -> {
            return $$04.decorState;
        }), BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter($$05 -> {
            return $$05.replaceableBlocks;
        }), Codec.BOOL.fieldOf("planted").orElse(false).forGetter($$06 -> {
            return Boolean.valueOf($$06.planted);
        })).apply($$0, (v1, v2, v3, v4, v5, v6) -> {
            return new HugeFungusConfiguration(v1, v2, v3, v4, v5, v6);
        });
    });
    public final BlockState validBaseState;
    public final BlockState stemState;
    public final BlockState hatState;
    public final BlockState decorState;
    public final BlockPredicate replaceableBlocks;
    public final boolean planted;

    public HugeFungusConfiguration(BlockState $$0, BlockState $$1, BlockState $$2, BlockState $$3, BlockPredicate $$4, boolean $$5) {
        this.validBaseState = $$0;
        this.stemState = $$1;
        this.hatState = $$2;
        this.decorState = $$3;
        this.replaceableBlocks = $$4;
        this.planted = $$5;
    }
}
