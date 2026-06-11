package net.minecraft.client.resources.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.SpecialBlockModelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ClientItemInfoLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Crypt;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.Zone;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelManager.class */
public class ModelManager implements PreparableReloadListener {
    public static final Identifier BLOCK_OR_ITEM = Identifier.withDefaultNamespace("block_or_item");
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter MODEL_LISTER = FileToIdConverter.json("models");
    private final AtlasManager atlasManager;
    private final PlayerSkinRenderCache playerSkinRenderCache;
    private final BlockColors blockColors;
    private ModelBakery.MissingModels missingModels;
    private Map<Identifier, ItemModel> bakedItemStackModels = Map.of();
    private Map<Identifier, ClientItem.Properties> itemProperties = Map.of();
    private EntityModelSet entityModelSet = EntityModelSet.EMPTY;
    private SpecialBlockModelRenderer specialBlockModelRenderer = SpecialBlockModelRenderer.EMPTY;
    private Object2IntMap<BlockState> modelGroups = Object2IntMaps.emptyMap();
    private final BlockModelShaper blockModelShaper = new BlockModelShaper(this);

    public ModelManager(BlockColors $$0, AtlasManager $$1, PlayerSkinRenderCache $$2) {
        this.blockColors = $$0;
        this.atlasManager = $$1;
        this.playerSkinRenderCache = $$2;
    }

    public BlockStateModel getMissingBlockStateModel() {
        return this.missingModels.block();
    }

    public ItemModel getItemModel(Identifier $$0) {
        return this.bakedItemStackModels.getOrDefault($$0, this.missingModels.item());
    }

    public ClientItem.Properties getItemProperties(Identifier $$0) {
        return this.itemProperties.getOrDefault($$0, ClientItem.Properties.DEFAULT);
    }

    public BlockModelShaper getBlockModelShaper() {
        return this.blockModelShaper;
    }

    @Override // net.minecraft.server.packs.resources.PreparableReloadListener
    public final CompletableFuture<Void> reload(PreparableReloadListener.SharedState $$0, Executor $$1, PreparableReloadListener.PreparationBarrier $$2, Executor $$3) {
        ResourceManager $$4 = $$0.resourceManager();
        CompletableFuture<EntityModelSet> $$5 = CompletableFuture.supplyAsync(EntityModelSet::vanilla, $$1);
        CompletableFuture completableFutureThenApplyAsync = $$5.thenApplyAsync((Function<? super EntityModelSet, ? extends U>) $$02 -> {
            return SpecialBlockModelRenderer.vanilla(new SpecialModelRenderer.BakingContext.Simple($$02, this.atlasManager, this.playerSkinRenderCache));
        }, $$1);
        CompletableFuture<Map<Identifier, UnbakedModel>> $$7 = loadBlockModels($$4, $$1);
        CompletableFuture<BlockStateModelLoader.LoadedModels> $$8 = BlockStateModelLoader.loadBlockStates($$4, $$1);
        CompletableFuture<ClientItemInfoLoader.LoadedClientInfos> $$9 = ClientItemInfoLoader.scheduleLoad($$4, $$1);
        CompletableFuture completableFutureThenApplyAsync2 = CompletableFuture.allOf($$7, $$8, $$9).thenApplyAsync($$32 -> {
            return discoverModelDependencies((Map) $$7.join(), (BlockStateModelLoader.LoadedModels) $$8.join(), (ClientItemInfoLoader.LoadedClientInfos) $$9.join());
        }, $$1);
        CompletableFuture completableFutureThenApplyAsync3 = $$8.thenApplyAsync($$03 -> {
            return buildModelGroups(this.blockColors, $$03);
        }, $$1);
        AtlasManager.PendingStitchResults $$12 = (AtlasManager.PendingStitchResults) $$0.get(AtlasManager.PENDING_STITCH);
        CompletableFuture<SpriteLoader.Preparations> $$13 = $$12.get(AtlasIds.BLOCKS);
        CompletableFuture<SpriteLoader.Preparations> $$14 = $$12.get(AtlasIds.ITEMS);
        CompletableFuture<U> completableFutureThenComposeAsync = CompletableFuture.allOf($$13, $$14, completableFutureThenApplyAsync2, completableFutureThenApplyAsync3, $$8, $$9, $$5, completableFutureThenApplyAsync, $$7).thenComposeAsync($$10 -> {
            SpriteLoader.Preparations $$11 = (SpriteLoader.Preparations) $$13.join();
            SpriteLoader.Preparations $$122 = (SpriteLoader.Preparations) $$14.join();
            ResolvedModels $$132 = (ResolvedModels) completableFutureThenApplyAsync2.join();
            Object2IntMap<BlockState> $$142 = (Object2IntMap) completableFutureThenApplyAsync3.join();
            Sets.SetView setViewDifference = Sets.difference(((Map) $$7.join()).keySet(), $$132.models.keySet());
            if (!setViewDifference.isEmpty()) {
                LOGGER.debug("Unreferenced models: \n{}", setViewDifference.stream().sorted().map($$04 -> {
                    return "\t" + String.valueOf($$04) + "\n";
                }).collect(Collectors.joining()));
            }
            ModelBakery $$16 = new ModelBakery((EntityModelSet) $$5.join(), this.atlasManager, this.playerSkinRenderCache, ((BlockStateModelLoader.LoadedModels) $$8.join()).models(), ((ClientItemInfoLoader.LoadedClientInfos) $$9.join()).contents(), $$132.models(), $$132.missing());
            return loadModels($$11, $$122, $$16, $$142, (EntityModelSet) $$5.join(), (SpecialBlockModelRenderer) completableFutureThenApplyAsync.join(), $$1);
        }, $$1);
        Objects.requireNonNull($$2);
        return completableFutureThenComposeAsync.thenCompose((Function<? super U, ? extends CompletionStage<U>>) (v1) -> {
            return r1.wait(v1);
        }).thenAcceptAsync(this::apply, $$3);
    }

    private static CompletableFuture<Map<Identifier, UnbakedModel>> loadBlockModels(ResourceManager $$0, Executor $$1) {
        return CompletableFuture.supplyAsync(() -> {
            return MODEL_LISTER.listMatchingResources($$0);
        }, $$1).thenCompose($$12 -> {
            List<CompletableFuture<Pair<Identifier, BlockModel>>> $$2 = new ArrayList<>($$12.size());
            for (Map.Entry<Identifier, Resource> $$3 : $$12.entrySet()) {
                $$2.add(CompletableFuture.supplyAsync(() -> {
                    Identifier $$12 = MODEL_LISTER.fileToId((Identifier) $$3.getKey());
                    try {
                        Reader $$22 = ((Resource) $$3.getValue()).openAsReader();
                        try {
                            Pair pairOf = Pair.of($$12, BlockModel.fromStream($$22));
                            if ($$22 != null) {
                                $$22.close();
                            }
                            return pairOf;
                        } finally {
                        }
                    } catch (Exception $$32) {
                        LOGGER.error("Failed to load model {}", $$3.getKey(), $$32);
                        return null;
                    }
                }, $$1));
            }
            return Util.sequence($$2).thenApply($$02 -> {
                return (Map) $$02.stream().filter((v0) -> {
                    return Objects.nonNull(v0);
                }).collect(Collectors.toUnmodifiableMap((v0) -> {
                    return v0.getFirst();
                }, (v0) -> {
                    return v0.getSecond();
                }));
            });
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ResolvedModels discoverModelDependencies(Map<Identifier, UnbakedModel> $$0, BlockStateModelLoader.LoadedModels $$1, ClientItemInfoLoader.LoadedClientInfos $$2) {
        Zone $$3 = Profiler.get().zone("dependencies");
        try {
            ModelDiscovery $$4 = new ModelDiscovery($$0, MissingBlockModel.missingModel());
            $$4.addSpecialModel(ItemModelGenerator.GENERATED_ITEM_MODEL_ID, new ItemModelGenerator());
            Collection<BlockStateModel.UnbakedRoot> collectionValues = $$1.models().values();
            Objects.requireNonNull($$4);
            collectionValues.forEach((v1) -> {
                r1.addRoot(v1);
            });
            $$2.contents().values().forEach($$12 -> {
                $$4.addRoot($$12.model());
            });
            ResolvedModels resolvedModels = new ResolvedModels($$4.missingModel(), $$4.resolve());
            if ($$3 != null) {
                $$3.close();
            }
            return resolvedModels;
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static CompletableFuture<ReloadState> loadModels(final SpriteLoader.Preparations $$0, final SpriteLoader.Preparations $$1, ModelBakery $$2, Object2IntMap<BlockState> $$3, EntityModelSet $$4, SpecialBlockModelRenderer $$5, Executor $$6) {
        final Multimap<String, Material> $$7 = Multimaps.synchronizedMultimap(HashMultimap.create());
        final Multimap<String, String> $$8 = Multimaps.synchronizedMultimap(HashMultimap.create());
        return $$2.bakeModels(new SpriteGetter() { // from class: net.minecraft.client.resources.model.ModelManager.1
            private final TextureAtlasSprite blockMissing;
            private final TextureAtlasSprite itemMissing;

            {
                this.blockMissing = $$0.missing();
                this.itemMissing = $$1.missing();
            }

            @Override // net.minecraft.client.resources.model.SpriteGetter
            public TextureAtlasSprite get(Material $$02, ModelDebugName $$12) {
                TextureAtlasSprite $$62;
                TextureAtlasSprite $$72;
                Identifier $$22 = $$02.atlasLocation();
                boolean $$32 = $$22.equals(ModelManager.BLOCK_OR_ITEM);
                boolean $$42 = $$22.equals(TextureAtlas.LOCATION_ITEMS);
                boolean $$52 = $$22.equals(TextureAtlas.LOCATION_BLOCKS);
                if (($$32 || $$42) && ($$62 = $$1.getSprite($$02.texture())) != null) {
                    return $$62;
                }
                if (($$32 || $$52) && ($$72 = $$0.getSprite($$02.texture())) != null) {
                    return $$72;
                }
                $$7.put($$12.debugName(), $$02);
                return $$42 ? this.itemMissing : this.blockMissing;
            }

            @Override // net.minecraft.client.resources.model.SpriteGetter
            public TextureAtlasSprite reportMissingReference(String $$02, ModelDebugName $$12) {
                $$8.put($$12.debugName(), $$02);
                return this.blockMissing;
            }
        }, $$6).thenApply($$52 -> {
            $$7.asMap().forEach(($$02, $$12) -> {
                LOGGER.warn("Missing textures in model {}:\n{}", $$02, $$12.stream().sorted(Material.COMPARATOR).map($$02 -> {
                    return "    " + String.valueOf($$02.atlasLocation()) + ":" + String.valueOf($$02.texture());
                }).collect(Collectors.joining(Crypt.MIME_LINE_SEPARATOR)));
            });
            $$8.asMap().forEach(($$03, $$13) -> {
                LOGGER.warn("Missing texture references in model {}:\n{}", $$03, $$13.stream().sorted().map($$03 -> {
                    return "    " + $$03;
                }).collect(Collectors.joining(Crypt.MIME_LINE_SEPARATOR)));
            });
            Map<BlockState, BlockStateModel> $$62 = createBlockStateToModelDispatch($$52.blockStateModels(), $$52.missingModels().block());
            return new ReloadState($$52, $$3, $$62, $$4, $$5);
        });
    }

    private static Map<BlockState, BlockStateModel> createBlockStateToModelDispatch(Map<BlockState, BlockStateModel> $$0, BlockStateModel $$1) {
        Zone $$2 = Profiler.get().zone("block state dispatch");
        try {
            Map<BlockState, BlockStateModel> $$3 = new IdentityHashMap<>($$0);
            for (Block $$4 : BuiltInRegistries.BLOCK) {
                $$4.getStateDefinition().getPossibleStates().forEach($$22 -> {
                    if ($$0.putIfAbsent($$22, $$1) == null) {
                        LOGGER.warn("Missing model for variant: '{}'", $$22);
                    }
                });
            }
            if ($$2 != null) {
                $$2.close();
            }
            return $$3;
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static Object2IntMap<BlockState> buildModelGroups(BlockColors $$0, BlockStateModelLoader.LoadedModels $$1) {
        Zone $$2 = Profiler.get().zone("block groups");
        try {
            Object2IntMap<BlockState> object2IntMapBuild = ModelGroupCollector.build($$0, $$1);
            if ($$2 != null) {
                $$2.close();
            }
            return object2IntMapBuild;
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void apply(ReloadState $$0) {
        ModelBakery.BakingResult $$1 = $$0.bakedModels;
        this.bakedItemStackModels = $$1.itemStackModels();
        this.itemProperties = $$1.itemProperties();
        this.modelGroups = $$0.modelGroups;
        this.missingModels = $$1.missingModels();
        this.blockModelShaper.replaceCache($$0.modelCache);
        this.specialBlockModelRenderer = $$0.specialBlockModelRenderer;
        this.entityModelSet = $$0.entityModelSet;
    }

    public boolean requiresRender(BlockState $$0, BlockState $$1) {
        if ($$0 == $$1) {
            return false;
        }
        int $$2 = this.modelGroups.getInt($$0);
        if ($$2 != -1) {
            int $$3 = this.modelGroups.getInt($$1);
            if ($$2 == $$3) {
                FluidState $$4 = $$0.getFluidState();
                FluidState $$5 = $$1.getFluidState();
                return $$4 != $$5;
            }
            return true;
        }
        return true;
    }

    public SpecialBlockModelRenderer specialBlockModelRenderer() {
        return this.specialBlockModelRenderer;
    }

    public Supplier<EntityModelSet> entityModels() {
        return () -> {
            return this.entityModelSet;
        };
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelManager$ResolvedModels.class */
    static final class ResolvedModels extends Record {
        private final ResolvedModel missing;
        private final Map<Identifier, ResolvedModel> models;

        ResolvedModels(ResolvedModel $$0, Map<Identifier, ResolvedModel> $$1) {
            this.missing = $$0;
            this.models = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ResolvedModels.class), ResolvedModels.class, "missing;models", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->missing:Lnet/minecraft/client/resources/model/ResolvedModel;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->models:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ResolvedModels.class), ResolvedModels.class, "missing;models", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->missing:Lnet/minecraft/client/resources/model/ResolvedModel;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->models:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ResolvedModels.class, Object.class), ResolvedModels.class, "missing;models", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->missing:Lnet/minecraft/client/resources/model/ResolvedModel;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ResolvedModels;->models:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResolvedModel missing() {
            return this.missing;
        }

        public Map<Identifier, ResolvedModel> models() {
            return this.models;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelManager$ReloadState.class */
    static final class ReloadState extends Record {
        private final ModelBakery.BakingResult bakedModels;
        private final Object2IntMap<BlockState> modelGroups;
        private final Map<BlockState, BlockStateModel> modelCache;
        private final EntityModelSet entityModelSet;
        private final SpecialBlockModelRenderer specialBlockModelRenderer;

        ReloadState(ModelBakery.BakingResult $$0, Object2IntMap<BlockState> $$1, Map<BlockState, BlockStateModel> $$2, EntityModelSet $$3, SpecialBlockModelRenderer $$4) {
            this.bakedModels = $$0;
            this.modelGroups = $$1;
            this.modelCache = $$2;
            this.entityModelSet = $$3;
            this.specialBlockModelRenderer = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ReloadState.class), ReloadState.class, "bakedModels;modelGroups;modelCache;entityModelSet;specialBlockModelRenderer", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->bakedModels:Lnet/minecraft/client/resources/model/ModelBakery$BakingResult;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelGroups:Lit/unimi/dsi/fastutil/objects/Object2IntMap;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelCache:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->specialBlockModelRenderer:Lnet/minecraft/client/renderer/SpecialBlockModelRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ReloadState.class), ReloadState.class, "bakedModels;modelGroups;modelCache;entityModelSet;specialBlockModelRenderer", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->bakedModels:Lnet/minecraft/client/resources/model/ModelBakery$BakingResult;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelGroups:Lit/unimi/dsi/fastutil/objects/Object2IntMap;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelCache:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->specialBlockModelRenderer:Lnet/minecraft/client/renderer/SpecialBlockModelRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ReloadState.class, Object.class), ReloadState.class, "bakedModels;modelGroups;modelCache;entityModelSet;specialBlockModelRenderer", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->bakedModels:Lnet/minecraft/client/resources/model/ModelBakery$BakingResult;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelGroups:Lit/unimi/dsi/fastutil/objects/Object2IntMap;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->modelCache:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/resources/model/ModelManager$ReloadState;->specialBlockModelRenderer:Lnet/minecraft/client/renderer/SpecialBlockModelRenderer;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ModelBakery.BakingResult bakedModels() {
            return this.bakedModels;
        }

        public Object2IntMap<BlockState> modelGroups() {
            return this.modelGroups;
        }

        public Map<BlockState, BlockStateModel> modelCache() {
            return this.modelCache;
        }

        public EntityModelSet entityModelSet() {
            return this.entityModelSet;
        }

        public SpecialBlockModelRenderer specialBlockModelRenderer() {
            return this.specialBlockModelRenderer;
        }
    }
}
