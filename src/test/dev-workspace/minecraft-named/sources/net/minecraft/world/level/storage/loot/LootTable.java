package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/LootTable.class */
public class LootTable {
    public static final long RANDOMIZE_SEED = 0;
    private final ContextKeySet paramSet;
    private final Optional<Identifier> randomSequence;
    private final List<LootPool> pools;
    private final List<LootItemFunction> functions;
    private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<ResourceKey<LootTable>> KEY_CODEC = ResourceKey.codec(Registries.LOOT_TABLE);
    public static final ContextKeySet DEFAULT_PARAM_SET = LootContextParamSets.ALL_PARAMS;
    public static final Codec<LootTable> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        return RecordCodecBuilder.create($$0 -> {
            return $$0.group(LootContextParamSets.CODEC.lenientOptionalFieldOf(ChunkRegionIoEvent.Fields.TYPE, DEFAULT_PARAM_SET).forGetter($$0 -> {
                return $$0.paramSet;
            }), Identifier.CODEC.optionalFieldOf("random_sequence").forGetter($$02 -> {
                return $$02.randomSequence;
            }), LootPool.CODEC.listOf().optionalFieldOf("pools", List.of()).forGetter($$03 -> {
                return $$03.pools;
            }), LootItemFunctions.ROOT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter($$04 -> {
                return $$04.functions;
            })).apply($$0, LootTable::new);
        });
    });
    public static final Codec<Holder<LootTable>> CODEC = RegistryFileCodec.create(Registries.LOOT_TABLE, DIRECT_CODEC);
    public static final LootTable EMPTY = new LootTable(LootContextParamSets.EMPTY, Optional.empty(), List.of(), List.of());

    LootTable(ContextKeySet $$0, Optional<Identifier> $$1, List<LootPool> $$2, List<LootItemFunction> $$3) {
        this.paramSet = $$0;
        this.randomSequence = $$1;
        this.pools = $$2;
        this.functions = $$3;
        this.compositeFunction = LootItemFunctions.compose($$3);
    }

    public static Consumer<ItemStack> createStackSplitter(ServerLevel $$0, Consumer<ItemStack> $$1) {
        return $$2 -> {
            if (!$$2.isItemEnabled($$0.enabledFeatures())) {
                return;
            }
            if ($$2.getCount() < $$2.getMaxStackSize()) {
                $$1.accept($$2);
                return;
            }
            int $$3 = $$2.getCount();
            while ($$3 > 0) {
                ItemStack $$4 = $$2.copyWithCount(Math.min($$2.getMaxStackSize(), $$3));
                $$3 -= $$4.getCount();
                $$1.accept($$4);
            }
        };
    }

    public void getRandomItemsRaw(LootParams $$0, Consumer<ItemStack> $$1) {
        getRandomItemsRaw(new LootContext.Builder($$0).create(this.randomSequence), $$1);
    }

    public void getRandomItemsRaw(LootContext $$0, Consumer<ItemStack> $$1) {
        LootContext.VisitedEntry<?> $$2 = LootContext.createVisitedEntry(this);
        if ($$0.pushVisitedElement($$2)) {
            Consumer<ItemStack> $$3 = LootItemFunction.decorate(this.compositeFunction, $$1, $$0);
            for (LootPool $$4 : this.pools) {
                $$4.addRandomItems($$3, $$0);
            }
            $$0.popVisitedElement($$2);
            return;
        }
        LOGGER.warn("Detected infinite loop in loot tables");
    }

    public void getRandomItems(LootParams $$0, long $$1, Consumer<ItemStack> $$2) {
        getRandomItemsRaw(new LootContext.Builder($$0).withOptionalRandomSeed($$1).create(this.randomSequence), createStackSplitter($$0.getLevel(), $$2));
    }

    public void getRandomItems(LootParams $$0, Consumer<ItemStack> $$1) {
        getRandomItemsRaw($$0, createStackSplitter($$0.getLevel(), $$1));
    }

    public void getRandomItems(LootContext $$0, Consumer<ItemStack> $$1) {
        getRandomItemsRaw($$0, createStackSplitter($$0.getLevel(), $$1));
    }

    public ObjectArrayList<ItemStack> getRandomItems(LootParams $$0, RandomSource $$1) {
        return getRandomItems(new LootContext.Builder($$0).withOptionalRandomSource($$1).create(this.randomSequence));
    }

    public ObjectArrayList<ItemStack> getRandomItems(LootParams $$0, long $$1) {
        return getRandomItems(new LootContext.Builder($$0).withOptionalRandomSeed($$1).create(this.randomSequence));
    }

    public ObjectArrayList<ItemStack> getRandomItems(LootParams $$0) {
        return getRandomItems(new LootContext.Builder($$0).create(this.randomSequence));
    }

    private ObjectArrayList<ItemStack> getRandomItems(LootContext $$0) {
        ObjectArrayList<ItemStack> $$1 = new ObjectArrayList<>();
        Objects.requireNonNull($$1);
        getRandomItems($$0, (v1) -> {
            r2.add(v1);
        });
        return $$1;
    }

    public ContextKeySet getParamSet() {
        return this.paramSet;
    }

    public void validate(ValidationContext $$0) {
        for (int $$1 = 0; $$1 < this.pools.size(); $$1++) {
            this.pools.get($$1).validate($$0.forChild(new ProblemReporter.IndexedFieldPathElement("pools", $$1)));
        }
        for (int $$2 = 0; $$2 < this.functions.size(); $$2++) {
            this.functions.get($$2).validate($$0.forChild(new ProblemReporter.IndexedFieldPathElement("functions", $$2)));
        }
    }

    public void fill(Container $$0, LootParams $$1, long $$2) {
        LootContext $$3 = new LootContext.Builder($$1).withOptionalRandomSeed($$2).create(this.randomSequence);
        ObjectArrayList<ItemStack> $$4 = getRandomItems($$3);
        RandomSource $$5 = $$3.getRandom();
        List<Integer> $$6 = getAvailableSlots($$0, $$5);
        shuffleAndSplitItems($$4, $$6.size(), $$5);
        ObjectListIterator it = $$4.iterator();
        while (it.hasNext()) {
            ItemStack $$7 = (ItemStack) it.next();
            if ($$6.isEmpty()) {
                LOGGER.warn("Tried to over-fill a container");
                return;
            } else if ($$7.isEmpty()) {
                $$0.setItem($$6.remove($$6.size() - 1).intValue(), ItemStack.EMPTY);
            } else {
                $$0.setItem($$6.remove($$6.size() - 1).intValue(), $$7);
            }
        }
    }

    private void shuffleAndSplitItems(ObjectArrayList<ItemStack> $$0, int $$1, RandomSource $$2) {
        List<ItemStack> $$3 = Lists.newArrayList();
        ObjectListIterator it = $$0.iterator();
        while (it.hasNext()) {
            ItemStack $$5 = (ItemStack) it.next();
            if ($$5.isEmpty()) {
                it.remove();
            } else if ($$5.getCount() > 1) {
                $$3.add($$5);
                it.remove();
            }
        }
        while (($$1 - $$0.size()) - $$3.size() > 0 && !$$3.isEmpty()) {
            ItemStack $$6 = $$3.remove(Mth.nextInt($$2, 0, $$3.size() - 1));
            int $$7 = Mth.nextInt($$2, 1, $$6.getCount() / 2);
            ItemStack $$8 = $$6.split($$7);
            if ($$6.getCount() > 1 && $$2.nextBoolean()) {
                $$3.add($$6);
            } else {
                $$0.add($$6);
            }
            if ($$8.getCount() > 1 && $$2.nextBoolean()) {
                $$3.add($$8);
            } else {
                $$0.add($$8);
            }
        }
        $$0.addAll($$3);
        Util.shuffle($$0, $$2);
    }

    private List<Integer> getAvailableSlots(Container $$0, RandomSource $$1) {
        ObjectArrayList<Integer> $$2 = new ObjectArrayList<>();
        for (int $$3 = 0; $$3 < $$0.getContainerSize(); $$3++) {
            if ($$0.getItem($$3).isEmpty()) {
                $$2.add(Integer.valueOf($$3));
            }
        }
        Util.shuffle($$2, $$1);
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/LootTable$Builder.class */
    public static class Builder implements FunctionUserBuilder<Builder> {
        private final ImmutableList.Builder<LootPool> pools = ImmutableList.builder();
        private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
        private ContextKeySet paramSet = LootTable.DEFAULT_PARAM_SET;
        private Optional<Identifier> randomSequence = Optional.empty();

        public Builder withPool(LootPool.Builder $$0) {
            this.pools.add($$0.build());
            return this;
        }

        public Builder setParamSet(ContextKeySet $$0) {
            this.paramSet = $$0;
            return this;
        }

        public Builder setRandomSequence(Identifier $$0) {
            this.randomSequence = Optional.of($$0);
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder
        public Builder apply(LootItemFunction.Builder $$0) {
            this.functions.add($$0.build());
            return this;
        }

        @Override // net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder, net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder
        public Builder unwrap() {
            return this;
        }

        public LootTable build() {
            return new LootTable(this.paramSet, this.randomSequence, this.pools.build(), this.functions.build());
        }
    }

    public static Builder lootTable() {
        return new Builder();
    }
}
