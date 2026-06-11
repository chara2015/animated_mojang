package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/heightproviders/ConstantHeight.class */
public class ConstantHeight extends HeightProvider {
    public static final ConstantHeight ZERO = new ConstantHeight(VerticalAnchor.absolute(0));
    public static final MapCodec<ConstantHeight> CODEC = VerticalAnchor.CODEC.fieldOf("value").xmap(ConstantHeight::new, (v0) -> {
        return v0.getValue();
    });
    private final VerticalAnchor value;

    public static ConstantHeight of(VerticalAnchor $$0) {
        return new ConstantHeight($$0);
    }

    private ConstantHeight(VerticalAnchor $$0) {
        this.value = $$0;
    }

    public VerticalAnchor getValue() {
        return this.value;
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public int sample(RandomSource $$0, WorldGenerationContext $$1) {
        return this.value.resolveY($$1);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public HeightProviderType<?> getType() {
        return HeightProviderType.CONSTANT;
    }

    public String toString() {
        return this.value.toString();
    }
}
