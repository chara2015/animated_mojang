package net.minecraft.world.level.storage.loot.predicates;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/AnyOfCondition.class */
public class AnyOfCondition extends CompositeLootItemCondition {
    public static final MapCodec<AnyOfCondition> CODEC = createCodec(AnyOfCondition::new);

    AnyOfCondition(List<LootItemCondition> $$0) {
        super($$0, Util.anyOf($$0));
    }

    @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition
    public LootItemConditionType getType() {
        return LootItemConditions.ANY_OF;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/AnyOfCondition$Builder.class */
    public static class Builder extends CompositeLootItemCondition.Builder {
        public Builder(LootItemCondition.Builder... $$0) {
            super($$0);
        }

        @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder
        public Builder or(LootItemCondition.Builder $$0) {
            addTerm($$0);
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition.Builder
        protected LootItemCondition create(List<LootItemCondition> $$0) {
            return new AnyOfCondition($$0);
        }
    }

    public static Builder anyOf(LootItemCondition.Builder... $$0) {
        return new Builder($$0);
    }
}
