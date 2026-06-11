package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/rule/blockentity/RuleBlockEntityModifierType.class */
public interface RuleBlockEntityModifierType<P extends RuleBlockEntityModifier> {
    public static final RuleBlockEntityModifierType<Clear> CLEAR = register("clear", Clear.CODEC);
    public static final RuleBlockEntityModifierType<Passthrough> PASSTHROUGH = register("passthrough", Passthrough.CODEC);
    public static final RuleBlockEntityModifierType<AppendStatic> APPEND_STATIC = register("append_static", AppendStatic.CODEC);
    public static final RuleBlockEntityModifierType<AppendLoot> APPEND_LOOT = register("append_loot", AppendLoot.CODEC);

    MapCodec<P> codec();

    private static <P extends RuleBlockEntityModifier> RuleBlockEntityModifierType<P> register(String $$0, MapCodec<P> $$1) {
        return (RuleBlockEntityModifierType) Registry.register(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, $$0, () -> {
            return $$1;
        });
    }
}
