package net.minecraft.data.loot.packs;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/loot/packs/TradeRebalanceLootTableProvider.class */
public class TradeRebalanceLootTableProvider {
    public static LootTableProvider create(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
        return new LootTableProvider($$0, Set.of(), List.of(new LootTableProvider.SubProviderEntry(TradeRebalanceChestLoot::new, LootContextParamSets.CHEST)), $$1);
    }
}
