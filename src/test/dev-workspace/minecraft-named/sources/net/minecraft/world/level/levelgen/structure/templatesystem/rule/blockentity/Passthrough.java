package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;

import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/rule/blockentity/Passthrough.class */
public class Passthrough implements RuleBlockEntityModifier {
    public static final Passthrough INSTANCE = new Passthrough();
    public static final MapCodec<Passthrough> CODEC = MapCodec.unit(INSTANCE);

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public CompoundTag apply(RandomSource $$0, CompoundTag $$1) {
        return $$1;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public RuleBlockEntityModifierType<?> getType() {
        return RuleBlockEntityModifierType.PASSTHROUGH;
    }
}
