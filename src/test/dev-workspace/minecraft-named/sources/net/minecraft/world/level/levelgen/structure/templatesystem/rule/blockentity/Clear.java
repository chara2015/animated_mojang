package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;

import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/rule/blockentity/Clear.class */
public class Clear implements RuleBlockEntityModifier {
    private static final Clear INSTANCE = new Clear();
    public static final MapCodec<Clear> CODEC = MapCodec.unit(INSTANCE);

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public CompoundTag apply(RandomSource $$0, CompoundTag $$1) {
        return new CompoundTag();
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public RuleBlockEntityModifierType<?> getType() {
        return RuleBlockEntityModifierType.CLEAR;
    }
}
