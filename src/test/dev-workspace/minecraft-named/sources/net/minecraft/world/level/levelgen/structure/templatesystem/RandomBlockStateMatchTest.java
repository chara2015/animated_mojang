package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/RandomBlockStateMatchTest.class */
public class RandomBlockStateMatchTest extends RuleTest {
    public static final MapCodec<RandomBlockStateMatchTest> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(BlockState.CODEC.fieldOf(Display.BlockDisplay.TAG_BLOCK_STATE).forGetter($$0 -> {
            return $$0.blockState;
        }), Codec.FLOAT.fieldOf("probability").forGetter($$02 -> {
            return Float.valueOf($$02.probability);
        })).apply($$0, (v1, v2) -> {
            return new RandomBlockStateMatchTest(v1, v2);
        });
    });
    private final BlockState blockState;
    private final float probability;

    public RandomBlockStateMatchTest(BlockState $$0, float $$1) {
        this.blockState = $$0;
        this.probability = $$1;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    public boolean test(BlockState $$0, RandomSource $$1) {
        return $$0 == this.blockState && $$1.nextFloat() < this.probability;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    protected RuleTestType<?> getType() {
        return RuleTestType.RANDOM_BLOCKSTATE_TEST;
    }
}
