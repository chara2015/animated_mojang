package net.minecraft.world.level.storage.loot.providers.nbt;

import java.util.Set;
import net.minecraft.nbt.Tag;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/nbt/NbtProvider.class */
public interface NbtProvider {
    Tag get(LootContext lootContext);

    Set<ContextKey<?>> getReferencedContextParams();

    LootNbtProviderType getType();
}
