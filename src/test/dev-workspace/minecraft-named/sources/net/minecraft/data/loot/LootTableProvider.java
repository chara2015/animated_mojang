package net.minecraft.data.loot;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.Util;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.RandomSequence;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/loot/LootTableProvider.class */
public class LootTableProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final PackOutput.PathProvider pathProvider;
    private final Set<ResourceKey<LootTable>> requiredTables;
    private final List<SubProviderEntry> subProviders;
    private final CompletableFuture<HolderLookup.Provider> registries;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/loot/LootTableProvider$SubProviderEntry.class */
    public static final class SubProviderEntry extends Record {
        private final Function<HolderLookup.Provider, LootTableSubProvider> provider;
        private final ContextKeySet paramSet;

        public SubProviderEntry(Function<HolderLookup.Provider, LootTableSubProvider> $$0, ContextKeySet $$1) {
            this.provider = $$0;
            this.paramSet = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SubProviderEntry.class), SubProviderEntry.class, "provider;paramSet", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->provider:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->paramSet:Lnet/minecraft/util/context/ContextKeySet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SubProviderEntry.class), SubProviderEntry.class, "provider;paramSet", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->provider:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->paramSet:Lnet/minecraft/util/context/ContextKeySet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SubProviderEntry.class, Object.class), SubProviderEntry.class, "provider;paramSet", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->provider:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;->paramSet:Lnet/minecraft/util/context/ContextKeySet;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Function<HolderLookup.Provider, LootTableSubProvider> provider() {
            return this.provider;
        }

        public ContextKeySet paramSet() {
            return this.paramSet;
        }
    }

    public LootTableProvider(PackOutput $$0, Set<ResourceKey<LootTable>> $$1, List<SubProviderEntry> $$2, CompletableFuture<HolderLookup.Provider> $$3) {
        this.pathProvider = $$0.createRegistryElementsPathProvider(Registries.LOOT_TABLE);
        this.subProviders = $$2;
        this.requiredTables = $$1;
        this.registries = $$3;
    }

    @Override // net.minecraft.data.DataProvider
    public CompletableFuture<?> run(CachedOutput $$0) {
        return this.registries.thenCompose($$1 -> {
            return run($$0, $$1);
        });
    }

    private CompletableFuture<?> run(CachedOutput $$0, HolderLookup.Provider $$1) {
        WritableRegistry<LootTable> $$2 = new MappedRegistry<>(Registries.LOOT_TABLE, Lifecycle.experimental());
        Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
        this.subProviders.forEach($$3 -> {
            $$3.provider().apply($$1).generate(($$3, $$4) -> {
                Identifier $$5 = sequenceIdForLootTable($$3);
                Identifier $$6 = (Identifier) object2ObjectOpenHashMap.put(RandomSequence.seedForKey($$5), $$5);
                if ($$6 != null) {
                    Util.logAndPauseIfInIde("Loot table random sequence seed collision on " + String.valueOf($$6) + " and " + String.valueOf($$3.identifier()));
                }
                $$4.setRandomSequence($$5);
                LootTable $$7 = $$4.setParamSet($$3.paramSet).build();
                $$2.register((ResourceKey<LootTable>) $$3, $$7, RegistrationInfo.BUILT_IN);
            });
        });
        $$2.freeze();
        ProblemReporter.Collector $$4 = new ProblemReporter.Collector();
        HolderGetter.Provider $$5 = new RegistryAccess.ImmutableRegistryAccess((List<? extends Registry<?>>) List.of($$2)).freeze();
        ValidationContext $$6 = new ValidationContext($$4, LootContextParamSets.ALL_PARAMS, $$5);
        for (ResourceKey<LootTable> $$8 : Sets.difference(this.requiredTables, $$2.registryKeySet())) {
            $$4.report(new MissingTableProblem($$8));
        }
        $$2.listElements().forEach($$12 -> {
            ((LootTable) $$12.value()).validate($$6.setContextKeySet(((LootTable) $$12.value()).getParamSet()).enterElement(new ProblemReporter.RootElementPathElement($$12.key()), $$12.key()));
        });
        if (!$$4.isEmpty()) {
            $$4.forEach(($$02, $$13) -> {
                LOGGER.warn("Found validation problem in {}: {}", $$02, $$13.description());
            });
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        return CompletableFuture.allOf((CompletableFuture[]) $$2.entrySet().stream().map($$22 -> {
            ResourceKey<LootTable> $$32 = (ResourceKey) $$22.getKey();
            LootTable $$42 = (LootTable) $$22.getValue();
            Path $$52 = this.pathProvider.json($$32.identifier());
            return DataProvider.saveStable($$0, $$1, LootTable.DIRECT_CODEC, $$42, $$52);
        }).toArray($$03 -> {
            return new CompletableFuture[$$03];
        }));
    }

    private static Identifier sequenceIdForLootTable(ResourceKey<LootTable> $$0) {
        return $$0.identifier();
    }

    @Override // net.minecraft.data.DataProvider
    public final String getName() {
        return "Loot Tables";
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/loot/LootTableProvider$MissingTableProblem.class */
    public static final class MissingTableProblem extends Record implements ProblemReporter.Problem {
        private final ResourceKey<LootTable> id;

        public MissingTableProblem(ResourceKey<LootTable> $$0) {
            this.id = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MissingTableProblem.class), MissingTableProblem.class, "id", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$MissingTableProblem;->id:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MissingTableProblem.class), MissingTableProblem.class, "id", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$MissingTableProblem;->id:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MissingTableProblem.class, Object.class), MissingTableProblem.class, "id", "FIELD:Lnet/minecraft/data/loot/LootTableProvider$MissingTableProblem;->id:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<LootTable> id() {
            return this.id;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Missing built-in table: " + String.valueOf(this.id.identifier());
        }
    }
}
