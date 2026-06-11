package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/EntryGroup.class */
public class EntryGroup extends CompositeEntryBase {
    public static final MapCodec<EntryGroup> CODEC = createCodec(EntryGroup::new);

    EntryGroup(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
    public LootPoolEntryType getType() {
        return LootPoolEntries.GROUP;
    }

    @Override // net.minecraft.world.level.storage.loot.entries.CompositeEntryBase
    protected ComposableEntryContainer compose(List<? extends ComposableEntryContainer> $$0) {
        switch ($$0.size()) {
            case 0:
                return ALWAYS_TRUE;
            case 1:
                return $$0.get(0);
            case 2:
                ComposableEntryContainer $$1 = $$0.get(0);
                ComposableEntryContainer $$2 = $$0.get(1);
                return ($$22, $$3) -> {
                    $$1.expand($$22, $$3);
                    $$2.expand($$22, $$3);
                    return true;
                };
            default:
                return ($$12, $$23) -> {
                    Iterator it = $$0.iterator();
                    while (it.hasNext()) {
                        ComposableEntryContainer $$32 = (ComposableEntryContainer) it.next();
                        $$32.expand($$12, $$23);
                    }
                    return true;
                };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/EntryGroup$Builder.class */
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
        public Builder append(LootPoolEntryContainer.Builder<?> $$0) {
            this.entries.add($$0.build());
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder
        public LootPoolEntryContainer build() {
            return new EntryGroup(this.entries.build(), getConditions());
        }
    }

    public static Builder list(LootPoolEntryContainer.Builder<?>... $$0) {
        return new Builder($$0);
    }
}
