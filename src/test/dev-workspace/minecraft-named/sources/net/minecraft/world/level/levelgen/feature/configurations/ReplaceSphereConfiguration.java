package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/ReplaceSphereConfiguration.class */
public class ReplaceSphereConfiguration implements FeatureConfiguration {
    public static final Codec<ReplaceSphereConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockState.CODEC.fieldOf(JigsawBlockEntity.TARGET).forGetter($$0 -> {
            return $$0.targetState;
        }), BlockState.CODEC.fieldOf(StructureTemplate.BLOCK_TAG_STATE).forGetter($$02 -> {
            return $$02.replaceState;
        }), IntProvider.codec(0, 12).fieldOf("radius").forGetter($$03 -> {
            return $$03.radius;
        })).apply($$0, ReplaceSphereConfiguration::new);
    });
    public final BlockState targetState;
    public final BlockState replaceState;
    private final IntProvider radius;

    public ReplaceSphereConfiguration(BlockState $$0, BlockState $$1, IntProvider $$2) {
        this.targetState = $$0;
        this.replaceState = $$1;
        this.radius = $$2;
    }

    public IntProvider radius() {
        return this.radius;
    }
}
