package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/CountPlacement.class */
public class CountPlacement extends RepeatingPlacement {
    public static final MapCodec<CountPlacement> CODEC = IntProvider.codec(0, 256).fieldOf("count").xmap(CountPlacement::new, $$0 -> {
        return $$0.count;
    });
    private final IntProvider count;

    private CountPlacement(IntProvider $$0) {
        this.count = $$0;
    }

    public static CountPlacement of(IntProvider $$0) {
        return new CountPlacement($$0);
    }

    public static CountPlacement of(int $$0) {
        return of(ConstantInt.of($$0));
    }

    @Override // net.minecraft.world.level.levelgen.placement.RepeatingPlacement
    protected int count(RandomSource $$0, BlockPos $$1) {
        return this.count.sample($$0);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.COUNT;
    }
}
