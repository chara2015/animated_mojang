package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/LootItemCondition.class */
public interface LootItemCondition extends LootContextUser, Predicate<LootContext> {
    public static final Codec<LootItemCondition> TYPED_CODEC = BuiltInRegistries.LOOT_CONDITION_TYPE.byNameCodec().dispatch("condition", (v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.codec();
    });
    public static final Codec<LootItemCondition> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        return Codec.withAlternative(TYPED_CODEC, AllOfCondition.INLINE_CODEC);
    });
    public static final Codec<Holder<LootItemCondition>> CODEC = RegistryFileCodec.create(Registries.PREDICATE, DIRECT_CODEC);

    LootItemConditionType getType();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/LootItemCondition$Builder.class */
    @FunctionalInterface
    public interface Builder {
        LootItemCondition build();

        default Builder invert() {
            return InvertedLootItemCondition.invert(this);
        }

        default AnyOfCondition.Builder or(Builder $$0) {
            return AnyOfCondition.anyOf(this, $$0);
        }

        default AllOfCondition.Builder and(Builder $$0) {
            return AllOfCondition.allOf(this, $$0);
        }
    }
}
