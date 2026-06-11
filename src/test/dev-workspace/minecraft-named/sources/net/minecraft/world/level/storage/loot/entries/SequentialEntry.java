package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/SequentialEntry.class */
public class SequentialEntry extends CompositeEntryBase {
    public static final MapCodec<SequentialEntry> CODEC = createCodec(SequentialEntry::new);

    SequentialEntry(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
    public LootPoolEntryType getType() {
        return LootPoolEntries.SEQUENCE;
    }

    @Override // net.minecraft.world.level.storage.loot.entries.CompositeEntryBase
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
        switch ($$0.size()) {
            case 0:
                return ALWAYS_TRUE;
            case 1:
                return $$0.get(0);
            case 2:
                return $$0.get(0).and($$0.get(1));
            default:
                return ($$1, $$2) -> {
                    Iterator it = $$0.iterator();
                    while (it.hasNext()) {
                        ComposableEntryContainer $$3 = (ComposableEntryContainer) it.next();
                        if (!$$3.expand($$1, $$2)) {
                            return false;
                        }
                    }
                    return true;
                };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/SequentialEntry$Builder.class */
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
        public Builder then(LootPoolEntryContainer.Builder<?> $$0) {
            this.entries.add($$0.build());
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder
        public LootPoolEntryContainer build() {
            return new SequentialEntry(this.entries.build(), getConditions());
        }
    }

    public static Builder sequential(LootPoolEntryContainer.Builder<?>... $$0) {
        return new Builder($$0);
    }
}
