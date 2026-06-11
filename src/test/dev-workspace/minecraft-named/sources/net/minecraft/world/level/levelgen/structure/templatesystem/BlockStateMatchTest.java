package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/BlockStateMatchTest.class */
public class BlockStateMatchTest extends RuleTest {
    public static final MapCodec<BlockStateMatchTest> CODEC = BlockState.CODEC.fieldOf(Display.BlockDisplay.TAG_BLOCK_STATE).xmap(BlockStateMatchTest::new, $$0 -> {
        return $$0.blockState;
    });
    private final BlockState blockState;

    public BlockStateMatchTest(BlockState $$0) {
        this.blockState = $$0;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    public boolean test(BlockState $$0, RandomSource $$1) {
        return $$0 == this.blockState;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    protected RuleTestType<?> getType() {
        return RuleTestType.BLOCKSTATE_TEST;
    }
}
