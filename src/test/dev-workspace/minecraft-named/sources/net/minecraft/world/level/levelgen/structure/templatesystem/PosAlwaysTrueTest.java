package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/PosAlwaysTrueTest.class */
public class PosAlwaysTrueTest extends PosRuleTest {
    public static final MapCodec<PosAlwaysTrueTest> CODEC = MapCodec.unit(() -> {
        return INSTANCE;
    });
    public static final PosAlwaysTrueTest INSTANCE = new PosAlwaysTrueTest();

    private PosAlwaysTrueTest() {
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest
    public boolean test(BlockPos $$0, BlockPos $$1, BlockPos $$2, RandomSource $$3) {
        return true;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest
    protected PosRuleTestType<?> getType() {
        return PosRuleTestType.ALWAYS_TRUE_TEST;
    }
}
