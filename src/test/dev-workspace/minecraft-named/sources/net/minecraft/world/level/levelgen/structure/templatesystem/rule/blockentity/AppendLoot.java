package net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.storage.loot.LootTable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/rule/blockentity/AppendLoot.class */
public class AppendLoot implements RuleBlockEntityModifier {
    public static final MapCodec<AppendLoot> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LootTable.KEY_CODEC.fieldOf("loot_table").forGetter($$0 -> {
            return $$0.lootTable;
        })).apply($$0, AppendLoot::new);
    });
    private final ResourceKey<LootTable> lootTable;

    public AppendLoot(ResourceKey<LootTable> $$0) {
        this.lootTable = $$0;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public CompoundTag apply(RandomSource $$0, CompoundTag $$1) {
        CompoundTag $$2 = $$1 == null ? new CompoundTag() : $$1.copy();
        $$2.store(RandomizableContainer.LOOT_TABLE_TAG, LootTable.KEY_CODEC, this.lootTable);
        $$2.putLong(RandomizableContainer.LOOT_TABLE_SEED_TAG, $$0.nextLong());
        return $$2;
    }

    @Override // net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier
    public RuleBlockEntityModifierType<?> getType() {
        return RuleBlockEntityModifierType.APPEND_LOOT;
    }
}
