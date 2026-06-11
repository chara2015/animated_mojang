package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/AlwaysTrueTest.class */
public class AlwaysTrueTest extends RuleTest {
    public static final MapCodec<AlwaysTrueTest> CODEC = MapCodec.unit(() -> {
        return INSTANCE;
    });
    public static final AlwaysTrueTest INSTANCE = new AlwaysTrueTest();

    private AlwaysTrueTest() {
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    public boolean test(BlockState $$0, RandomSource $$1) {
        return true;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
    protected RuleTestType<?> getType() {
        return RuleTestType.ALWAYS_TRUE_TEST;
    }
}
