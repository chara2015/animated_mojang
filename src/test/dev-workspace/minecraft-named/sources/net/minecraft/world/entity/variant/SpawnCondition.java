package net.minecraft.world.entity.variant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.variant.PriorityProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/SpawnCondition.class */
public interface SpawnCondition extends PriorityProvider.SelectorCondition<SpawnContext> {
    public static final Codec<SpawnCondition> CODEC = BuiltInRegistries.SPAWN_CONDITION_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });

    MapCodec<? extends SpawnCondition> codec();
}
