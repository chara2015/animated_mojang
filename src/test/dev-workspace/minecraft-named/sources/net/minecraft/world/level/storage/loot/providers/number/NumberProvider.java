package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/NumberProvider.class */
public interface NumberProvider extends LootContextUser {
    float getFloat(LootContext lootContext);

    LootNumberProviderType getType();

    default int getInt(LootContext $$0) {
        return Math.round(getFloat($$0));
    }
}
