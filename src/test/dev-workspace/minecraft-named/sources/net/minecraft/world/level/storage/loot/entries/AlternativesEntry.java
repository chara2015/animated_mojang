package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/AlternativesEntry.class */
public class AlternativesEntry extends CompositeEntryBase {
    public static final MapCodec<AlternativesEntry> CODEC = createCodec(AlternativesEntry::new);
    public static final ProblemReporter.Problem UNREACHABLE_PROBLEM = new ProblemReporter.Problem() { // from class: net.minecraft.world.level.storage.loot.entries.AlternativesEntry.1
        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Unreachable entry!";
        }
    };

    AlternativesEntry(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
    public LootPoolEntryType getType() {
        return LootPoolEntries.ALTERNATIVES;
    }

    @Override // net.minecraft.world.level.storage.loot.entries.CompositeEntryBase
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
        switch ($$0.size()) {
            case 0:
                return ALWAYS_FALSE;
            case 1:
                return $$0.get(0);
            case 2:
                return $$0.get(0).or($$0.get(1));
            default:
                return ($$1, $$2) -> {
                    Iterator it = $$0.iterator();
                    while (it.hasNext()) {
                        ComposableEntryContainer $$3 = (ComposableEntryContainer) it.next();
                        if ($$3.expand($$1, $$2)) {
                            return true;
                        }
                    }
                    return false;
                };
        }
    }

    @Override // net.minecraft.world.level.storage.loot.entries.CompositeEntryBase, net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        for (int $$1 = 0; $$1 < this.children.size() - 1; $$1++) {
            if (this.children.get($$1).conditions.isEmpty()) {
                $$0.reportProblem(UNREACHABLE_PROBLEM);
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/AlternativesEntry$Builder.class */
    public static class Builder extends LootPoolEntryContainer.Builder<Builder> {
        private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();

        public Builder(LootPoolEntryContainer.Builder<?>... $$0) {
            for (LootPoolEntryContainer.Builder<?> $$1 : $$0) {
                this.entries.add($$1.build());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder
        public Builder getThis() {
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder
        public Builder otherwise(LootPoolEntryContainer.Builder<?> $$0) {
            this.entries.add($$0.build());
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder
        public LootPoolEntryContainer build() {
            return new AlternativesEntry(this.entries.build(), getConditions());
        }
    }

    public static Builder alternatives(LootPoolEntryContainer.Builder<?>... $$0) {
        return new Builder($$0);
    }

    public static <E> Builder alternatives(Collection<E> $$0, Function<E, LootPoolEntryContainer.Builder<?>> $$1) {
        Stream<E> stream = $$0.stream();
        Objects.requireNonNull($$1);
        return new Builder((LootPoolEntryContainer.Builder[]) stream.map($$1::apply).toArray($$02 -> {
            return new LootPoolEntryContainer.Builder[$$02];
        }));
    }
}
