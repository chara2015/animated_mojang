package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/RuleTest.class */
public abstract class RuleTest {
    public static final Codec<RuleTest> CODEC = BuiltInRegistries.RULE_TEST.byNameCodec().dispatch("predicate_type", (v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.codec();
    });

    public abstract boolean test(BlockState blockState, RandomSource randomSource);

    protected abstract RuleTestType<?> getType();
}
