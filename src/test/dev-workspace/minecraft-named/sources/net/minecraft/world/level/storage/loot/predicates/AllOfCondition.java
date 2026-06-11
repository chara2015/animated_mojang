package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/AllOfCondition.class */
public class AllOfCondition extends CompositeLootItemCondition {
    public static final MapCodec<AllOfCondition> CODEC = createCodec(AllOfCondition::new);
    public static final Codec<AllOfCondition> INLINE_CODEC = createInlineCodec(AllOfCondition::new);

    AllOfCondition(List<LootItemCondition> $$0) {
        super($$0, Util.allOf($$0));
    }

    public static AllOfCondition allOf(List<LootItemCondition> $$0) {
        return new AllOfCondition(List.copyOf($$0));
    }

    @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition
    public LootItemConditionType getType() {
        return LootItemConditions.ALL_OF;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/AllOfCondition$Builder.class */
    public static class Builder extends CompositeLootItemCondition.Builder {
        public Builder(LootItemCondition.Builder... $$0) {
            super($$0);
        }

        @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder
        public Builder and(LootItemCondition.Builder $$0) {
            addTerm($$0);
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition.Builder
        protected LootItemCondition create(List<LootItemCondition> $$0) {
            return new AllOfCondition($$0);
        }
    }

    public static Builder allOf(LootItemCondition.Builder... $$0) {
        return new Builder($$0);
    }
}
