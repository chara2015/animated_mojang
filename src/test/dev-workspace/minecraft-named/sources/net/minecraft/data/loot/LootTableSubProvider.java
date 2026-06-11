package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/loot/LootTableSubProvider.class */
@FunctionalInterface
public interface LootTableSubProvider {
    void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer);
}
