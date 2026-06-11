package net.minecraft.world.item.slot;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/SlotSource.class */
public interface SlotSource extends LootContextUser {
    MapCodec<? extends SlotSource> codec();

    SlotCollection provide(LootContext lootContext);
}
