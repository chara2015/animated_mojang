package net.minecraft.world.level.storage.loot;

import java.util.Set;
import net.minecraft.util.context.ContextKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/LootContextUser.class */
public interface LootContextUser {
    default Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of();
    }

    default void validate(ValidationContext $$0) {
        $$0.validateContextUsage(this);
    }
}
