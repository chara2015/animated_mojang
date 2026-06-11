package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/rule/blockentity/AppendStatic.class */
public class AppendStatic implements RuleBlockEntityModifier {
    public static final MapCodec<AppendStatic> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(CompoundTag.CODEC.fieldOf("data").forGetter($$0 -> {
            return $$0.tag;
        })).apply($$0, AppendStatic::new);
    });
    private final CompoundTag tag;

    public AppendStatic(CompoundTag $$0) {
        this.tag = $$0;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public CompoundTag apply(RandomSource $$0, CompoundTag $$1) {
        return $$1 == null ? this.tag.copy() : $$1.merge(this.tag);
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public RuleBlockEntityModifierType<?> getType() {
        return RuleBlockEntityModifierType.APPEND_STATIC;
    }
}
