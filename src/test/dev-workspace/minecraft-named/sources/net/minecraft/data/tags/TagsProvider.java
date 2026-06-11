package net.minecraft.data.tags;

import com.google.common.collect.Maps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/tags/TagsProvider.class */
public abstract class TagsProvider<T> implements DataProvider {
    protected final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final CompletableFuture<Void> contentsDone;
    private final CompletableFuture<TagLookup<T>> parentProvider;
    protected final ResourceKey<? extends Registry<T>> registryKey;
    private final Map<Identifier, TagBuilder> builders;

    protected abstract void addTags(HolderLookup.Provider provider);

    protected TagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2) {
        this($$0, $$1, $$2, CompletableFuture.completedFuture(TagLookup.empty()));
    }

    protected TagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2, CompletableFuture<TagLookup<T>> $$3) {
        this.contentsDone = new CompletableFuture<>();
        this.builders = Maps.newLinkedHashMap();
        this.pathProvider = $$0.createRegistryTagsPathProvider($$1);
        this.registryKey = $$1;
        this.parentProvider = $$3;
        this.lookupProvider = $$2;
    }

    @Override // net.minecraft.data.DataProvider
    public final String getName() {
        return "Tags for " + String.valueOf(this.registryKey.identifier());
    }

    /* JADX INFO: renamed from: net.minecraft.data.tags.TagsProvider$1CombinedData, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/tags/TagsProvider$1CombinedData.class */
    static final class C1CombinedData<T> extends Record {
        private final HolderLookup.Provider contents;
        private final TagLookup<T> parent;

        C1CombinedData(HolderLookup.Provider $$0, TagLookup<T> $$1) {
            this.contents = $$0;
            this.parent = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, C1CombinedData.class), C1CombinedData.class, "contents;parent", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->contents:Lnet/minecraft/core/HolderLookup$Provider;", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->parent:Lnet/minecraft/data/tags/TagsProvider$TagLookup;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, C1CombinedData.class), C1CombinedData.class, "contents;parent", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->contents:Lnet/minecraft/core/HolderLookup$Provider;", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->parent:Lnet/minecraft/data/tags/TagsProvider$TagLookup;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, C1CombinedData.class, Object.class), C1CombinedData.class, "contents;parent", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->contents:Lnet/minecraft/core/HolderLookup$Provider;", "FIELD:Lnet/minecraft/data/tags/TagsProvider$1CombinedData;->parent:Lnet/minecraft/data/tags/TagsProvider$TagLookup;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public HolderLookup.Provider contents() {
            return this.contents;
        }

        public TagLookup<T> parent() {
            return this.parent;
        }
    }

    @Override // net.minecraft.data.DataProvider
    public CompletableFuture<?> run(CachedOutput $$0) {
        return createContentsProvider().thenApply($$02 -> {
            this.contentsDone.complete(null);
            return $$02;
        }).thenCombineAsync((CompletionStage) this.parentProvider, (BiFunction<? super U, ? super U, ? extends V>) ($$03, $$1) -> {
            return new C1CombinedData($$03, $$1);
        }, (Executor) Util.backgroundExecutor()).thenCompose($$12 -> {
            HolderLookup.RegistryLookup<T> $$2 = $$12.contents.lookupOrThrow((ResourceKey) this.registryKey);
            Predicate<Identifier> $$3 = $$12 -> {
                return $$2.get(ResourceKey.create(this.registryKey, $$12)).isPresent();
            };
            Predicate<Identifier> $$4 = $$13 -> {
                return this.builders.containsKey($$13) || $$12.parent.contains(TagKey.create(this.registryKey, $$13));
            };
            return CompletableFuture.allOf((CompletableFuture[]) this.builders.entrySet().stream().map($$42 -> {
                Identifier $$5 = (Identifier) $$42.getKey();
                TagBuilder $$6 = (TagBuilder) $$42.getValue();
                List<TagEntry> $$7 = $$6.build();
                List<TagEntry> $$8 = $$7.stream().filter($$22 -> {
                    return !$$22.verifyIfPresent($$3, $$4);
                }).toList();
                if (!$$8.isEmpty()) {
                    throw new IllegalArgumentException(String.format(Locale.ROOT, "Couldn't define tag %s as it is missing following references: %s", $$5, $$8.stream().map((v0) -> {
                        return Objects.toString(v0);
                    }).collect(Collectors.joining(","))));
                }
                Path $$9 = this.pathProvider.json($$5);
                return DataProvider.saveStable($$0, $$12.contents, TagFile.CODEC, new TagFile($$7, false), $$9);
            }).toArray($$04 -> {
                return new CompletableFuture[$$04];
            }));
        });
    }

    protected TagBuilder getOrCreateRawBuilder(TagKey<T> $$0) {
        return this.builders.computeIfAbsent($$0.location(), $$02 -> {
            return TagBuilder.create();
        });
    }

    public CompletableFuture<TagLookup<T>> contentsGetter() {
        return (CompletableFuture<TagLookup<T>>) this.contentsDone.thenApply($$0 -> {
            return $$0 -> {
                return Optional.ofNullable(this.builders.get($$0.location()));
            };
        });
    }

    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return this.lookupProvider.thenApply($$0 -> {
            this.builders.clear();
            addTags($$0);
            return $$0;
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/tags/TagsProvider$TagLookup.class */
    @FunctionalInterface
    public interface TagLookup<T> extends Function<TagKey<T>, Optional<TagBuilder>> {
        static <T> TagLookup<T> empty() {
            return $$0 -> {
                return Optional.empty();
            };
        }

        /* JADX WARN: Multi-variable type inference failed */
        default boolean contains(TagKey<T> tagKey) {
            return apply(tagKey).isPresent();
        }
    }
}
