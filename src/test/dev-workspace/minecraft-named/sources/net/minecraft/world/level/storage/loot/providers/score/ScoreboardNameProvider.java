package net.minecraft.world.level.storage.loot.providers.score;

import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.scores.ScoreHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/score/ScoreboardNameProvider.class */
public interface ScoreboardNameProvider {
    ScoreHolder getScoreHolder(LootContext lootContext);

    LootScoreProviderType getType();

    Set<ContextKey<?>> getReferencedContextParams();
}
