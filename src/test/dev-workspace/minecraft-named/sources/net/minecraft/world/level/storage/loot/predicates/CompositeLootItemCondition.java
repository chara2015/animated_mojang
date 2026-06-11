package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/CompositeLootItemCondition.class */
public abstract class CompositeLootItemCondition implements LootItemCondition {
    protected final List<LootItemCondition> terms;
    private final Predicate<LootContext> composedPredicate;

    protected CompositeLootItemCondition(List<LootItemCondition> $$0, Predicate<LootContext> $$1) {
        this.terms = $$0;
        this.composedPredicate = $$1;
    }

    protected static <T extends CompositeLootItemCondition> MapCodec<T> createCodec(Function<List<LootItemCondition>, T> $$0) {
        return RecordCodecBuilder.mapCodec($$1 -> {
            return $$1.group(LootItemCondition.DIRECT_CODEC.listOf().fieldOf("terms").forGetter($$02 -> {
                return $$02.terms;
            })).apply($$1, $$0);
        });
    }

    protected static <T extends CompositeLootItemCondition> Codec<T> createInlineCodec(Function<List<LootItemCondition>, T> $$0) {
        return LootItemCondition.DIRECT_CODEC.listOf().xmap($$0, $$02 -> {
            return $$02.terms;
        });
    }

    @Override // java.util.function.Predicate
    public final boolean test(LootContext $$0) {
        return this.composedPredicate.test($$0);
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        for (int $$1 = 0; $$1 < this.terms.size(); $$1++) {
            this.terms.get($$1).validate($$0.forChild(new ProblemReporter.IndexedFieldPathElement("terms", $$1)));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/CompositeLootItemCondition$Builder.class */
    public static abstract class Builder implements LootItemCondition.Builder {
        private final ImmutableList.Builder<LootItemCondition> terms = ImmutableList.builder();

        protected abstract LootItemCondition create(List<LootItemCondition> list);

        protected Builder(LootItemCondition.Builder... $$0) {
            for (LootItemCondition.Builder $$1 : $$0) {
                this.terms.add($$1.build());
            }
        }

        public void addTerm(LootItemCondition.Builder $$0) {
            this.terms.add($$0.build());
        }

        @Override // net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder
        public LootItemCondition build() {
            return create(this.terms.build());
        }
    }
}
