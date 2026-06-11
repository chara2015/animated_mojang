package net.minecraft.world.level.chunk;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Util;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.feature.FeatureCountTracker;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableBoolean;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/ChunkGenerator.class */
public abstract class ChunkGenerator {
    public static final Codec<ChunkGenerator> CODEC = BuiltInRegistries.CHUNK_GENERATOR.byNameCodec().dispatchStable((v0) -> {
        return v0.codec();
    }, Function.identity());
    protected final BiomeSource biomeSource;
    private final Supplier<List<FeatureSorter.StepFeatureData>> featuresPerStep;
    private final Function<Holder<Biome>, BiomeGenerationSettings> generationSettingsGetter;

    protected abstract MapCodec<? extends ChunkGenerator> codec();

    public abstract void applyCarvers(WorldGenRegion worldGenRegion, long j, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess);

    public abstract void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess);

    public abstract void spawnOriginalMobs(WorldGenRegion worldGenRegion);

    public abstract int getGenDepth();

    public abstract CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess);

    public abstract int getSeaLevel();

    public abstract int getMinY();

    public abstract int getBaseHeight(int i, int i2, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState);

    public abstract NoiseColumn getBaseColumn(int i, int i2, LevelHeightAccessor levelHeightAccessor, RandomState randomState);

    public abstract void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos);

    public ChunkGenerator(BiomeSource $$0) {
        this($$0, $$02 -> {
            return ((Biome) $$02.value()).getGenerationSettings();
        });
    }

    public ChunkGenerator(BiomeSource $$0, Function<Holder<Biome>, BiomeGenerationSettings> $$1) {
        this.biomeSource = $$0;
        this.generationSettingsGetter = $$1;
        this.featuresPerStep = Suppliers.memoize(() -> {
            return FeatureSorter.buildFeaturesPerStep(List.copyOf($$0.possibleBiomes()), $$12 -> {
                return ((BiomeGenerationSettings) $$1.apply($$12)).features();
            }, true);
        });
    }

    public void validate() {
        this.featuresPerStep.get();
    }

    public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> $$0, RandomState $$1, long $$2) {
        return ChunkGeneratorStructureState.createForNormal($$1, $$2, this.biomeSource, $$0);
    }

    public Optional<ResourceKey<MapCodec<? extends ChunkGenerator>>> getTypeNameForDataFixer() {
        return BuiltInRegistries.CHUNK_GENERATOR.getResourceKey(codec());
    }

    public CompletableFuture<ChunkAccess> createBiomes(RandomState $$0, Blender $$1, StructureManager $$2, ChunkAccess $$3) {
        return CompletableFuture.supplyAsync(() -> {
            $$3.fillBiomesFromNoise(this.biomeSource, $$0.sampler());
            return $$3;
        }, Util.backgroundExecutor().forName("init_biomes"));
    }

    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel $$0, HolderSet<Structure> $$1, BlockPos $$2, int $$3, boolean $$4) {
        if (SharedConstants.DEBUG_DISABLE_FEATURES) {
            return null;
        }
        ChunkGeneratorStructureState $$5 = $$0.getChunkSource().getGeneratorState();
        Object2ObjectArrayMap object2ObjectArrayMap = new Object2ObjectArrayMap();
        Iterator<Structure> it = $$1.iterator();
        while (it.hasNext()) {
            Holder<Structure> $$7 = (Holder) it.next();
            for (StructurePlacement $$8 : $$5.getPlacementsForStructure($$7)) {
                ((Set) object2ObjectArrayMap.computeIfAbsent($$8, $$02 -> {
                    return new ObjectArraySet();
                })).add($$7);
            }
        }
        if (object2ObjectArrayMap.isEmpty()) {
            return null;
        }
        Pair<BlockPos, Holder<Structure>> $$9 = null;
        double $$10 = Double.MAX_VALUE;
        StructureManager $$11 = $$0.structureManager();
        List<Map.Entry<StructurePlacement, Set<Holder<Structure>>>> $$12 = new ArrayList<>(object2ObjectArrayMap.size());
        for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> $$13 : object2ObjectArrayMap.entrySet()) {
            StructurePlacement $$14 = $$13.getKey();
            if ($$14 instanceof ConcentricRingsStructurePlacement) {
                ConcentricRingsStructurePlacement $$15 = (ConcentricRingsStructurePlacement) $$14;
                Pair<BlockPos, Holder<Structure>> $$16 = getNearestGeneratedStructure($$13.getValue(), $$0, $$11, $$2, $$4, $$15);
                if ($$16 != null) {
                    BlockPos $$17 = (BlockPos) $$16.getFirst();
                    double $$18 = $$2.distSqr($$17);
                    if ($$18 < $$10) {
                        $$10 = $$18;
                        $$9 = $$16;
                    }
                }
            } else if ($$14 instanceof RandomSpreadStructurePlacement) {
                $$12.add($$13);
            }
        }
        if (!$$12.isEmpty()) {
            int $$19 = SectionPos.blockToSectionCoord($$2.getX());
            int $$20 = SectionPos.blockToSectionCoord($$2.getZ());
            for (int $$21 = 0; $$21 <= $$3; $$21++) {
                boolean $$22 = false;
                for (Map.Entry<StructurePlacement, Set<Holder<Structure>>> $$23 : $$12) {
                    RandomSpreadStructurePlacement $$24 = (RandomSpreadStructurePlacement) $$23.getKey();
                    Pair<BlockPos, Holder<Structure>> $$25 = getNearestGeneratedStructure($$23.getValue(), $$0, $$11, $$19, $$20, $$21, $$4, $$5.getLevelSeed(), $$24);
                    if ($$25 != null) {
                        $$22 = true;
                        double $$26 = $$2.distSqr((Vec3i) $$25.getFirst());
                        if ($$26 < $$10) {
                            $$10 = $$26;
                            $$9 = $$25;
                        }
                    }
                }
                if ($$22) {
                    return $$9;
                }
            }
        }
        return $$9;
    }

    private Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> $$0, ServerLevel $$1, StructureManager $$2, BlockPos $$3, boolean $$4, ConcentricRingsStructurePlacement $$5) {
        Pair<BlockPos, Holder<Structure>> $$13;
        List<ChunkPos> $$6 = $$1.getChunkSource().getGeneratorState().getRingPositionsFor($$5);
        if ($$6 == null) {
            throw new IllegalStateException("Somehow tried to find structures for a placement that doesn't exist");
        }
        Pair<BlockPos, Holder<Structure>> $$7 = null;
        double $$8 = Double.MAX_VALUE;
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (ChunkPos $$10 : $$6) {
            $$9.set(SectionPos.sectionToBlockCoord($$10.x, 8), 32, SectionPos.sectionToBlockCoord($$10.z, 8));
            double $$11 = $$9.distSqr($$3);
            boolean $$12 = $$7 == null || $$11 < $$8;
            if ($$12 && ($$13 = getStructureGeneratingAt($$0, $$1, $$2, $$4, $$5, $$10)) != null) {
                $$7 = $$13;
                $$8 = $$11;
            }
        }
        return $$7;
    }

    private static Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> $$0, LevelReader $$1, StructureManager $$2, int $$3, int $$4, int $$5, boolean $$6, long $$7, RandomSpreadStructurePlacement $$8) {
        int $$9 = $$8.spacing();
        int $$10 = -$$5;
        while ($$10 <= $$5) {
            boolean $$11 = $$10 == (-$$5) || $$10 == $$5;
            int $$12 = -$$5;
            while ($$12 <= $$5) {
                boolean $$13 = $$12 == (-$$5) || $$12 == $$5;
                if ($$11 || $$13) {
                    int $$14 = $$3 + ($$9 * $$10);
                    int $$15 = $$4 + ($$9 * $$12);
                    ChunkPos $$16 = $$8.getPotentialStructureChunk($$7, $$14, $$15);
                    Pair<BlockPos, Holder<Structure>> $$17 = getStructureGeneratingAt($$0, $$1, $$2, $$6, $$8, $$16);
                    if ($$17 != null) {
                        return $$17;
                    }
                }
                $$12++;
            }
            $$10++;
        }
        return null;
    }

    private static Pair<BlockPos, Holder<Structure>> getStructureGeneratingAt(Set<Holder<Structure>> $$0, LevelReader $$1, StructureManager $$2, boolean $$3, StructurePlacement $$4, ChunkPos $$5) {
        for (Holder<Structure> $$6 : $$0) {
            StructureCheckResult $$7 = $$2.checkStructurePresence($$5, $$6.value(), $$4, $$3);
            if ($$7 != StructureCheckResult.START_NOT_PRESENT) {
                if (!$$3 && $$7 == StructureCheckResult.START_PRESENT) {
                    return Pair.of($$4.getLocatePos($$5), $$6);
                }
                ChunkAccess $$8 = $$1.getChunk($$5.x, $$5.z, ChunkStatus.STRUCTURE_STARTS);
                StructureStart $$9 = $$2.getStartForStructure(SectionPos.bottomOf($$8), $$6.value(), $$8);
                if ($$9 != null && $$9.isValid() && (!$$3 || tryAddReference($$2, $$9))) {
                    return Pair.of($$4.getLocatePos($$9.getChunkPos()), $$6);
                }
            }
        }
        return null;
    }

    private static boolean tryAddReference(StructureManager $$0, StructureStart $$1) {
        if ($$1.canBeReferenced()) {
            $$0.addReference($$1);
            return true;
        }
        return false;
    }

    public void applyBiomeDecoration(WorldGenLevel $$0, ChunkAccess $$1, StructureManager $$2) {
        ChunkPos $$3 = $$1.getPos();
        if (SharedConstants.debugVoidTerrain($$3)) {
            return;
        }
        SectionPos $$4 = SectionPos.of($$3, $$0.getMinSectionY());
        BlockPos $$5 = $$4.origin();
        Registry<Structure> $$6 = $$0.registryAccess().lookupOrThrow((ResourceKey) Registries.STRUCTURE);
        Map<Integer, List<Structure>> $$7 = (Map) $$6.stream().collect(Collectors.groupingBy($$02 -> {
            return Integer.valueOf($$02.step().ordinal());
        }));
        List<FeatureSorter.StepFeatureData> $$8 = this.featuresPerStep.get();
        WorldgenRandom $$9 = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
        long $$10 = $$9.setDecorationSeed($$0.getSeed(), $$5.getX(), $$5.getZ());
        ObjectArraySet<Holder<Biome>> objectArraySet = new ObjectArraySet();
        ChunkPos.rangeClosed($$4.chunk(), 1).forEach($$22 -> {
            ChunkAccess $$32 = $$0.getChunk($$22.x, $$22.z);
            for (LevelChunkSection $$42 : $$32.getSections()) {
                PalettedContainerRO<Holder<Biome>> biomes = $$42.getBiomes();
                Objects.requireNonNull(objectArraySet);
                biomes.getAll((v1) -> {
                    r1.add(v1);
                });
            }
        });
        objectArraySet.retainAll(this.biomeSource.possibleBiomes());
        int $$12 = $$8.size();
        try {
            Registry<PlacedFeature> $$13 = $$0.registryAccess().lookupOrThrow((ResourceKey) Registries.PLACED_FEATURE);
            int $$14 = Math.max(GenerationStep.Decoration.values().length, $$12);
            for (int $$15 = 0; $$15 < $$14; $$15++) {
                int $$16 = 0;
                if ($$2.shouldGenerateStructures()) {
                    List<Structure> $$17 = $$7.getOrDefault(Integer.valueOf($$15), Collections.emptyList());
                    for (Structure $$18 : $$17) {
                        $$9.setFeatureSeed($$10, $$16, $$15);
                        Supplier<String> $$19 = () -> {
                            Optional map = $$6.getResourceKey($$18).map((v0) -> {
                                return v0.toString();
                            });
                            Objects.requireNonNull($$18);
                            return (String) map.orElseGet($$18::toString);
                        };
                        try {
                            $$0.setCurrentlyGenerating($$19);
                            $$2.startsForStructure($$4, $$18).forEach($$52 -> {
                                $$52.placeInChunk($$0, $$2, this, $$9, getWritableArea($$1), $$3);
                            });
                            $$16++;
                        } catch (Exception $$20) {
                            CrashReport $$21 = CrashReport.forThrowable($$20, "Feature placement");
                            CrashReportCategory crashReportCategoryAddCategory = $$21.addCategory("Feature");
                            Objects.requireNonNull($$19);
                            crashReportCategoryAddCategory.setDetail("Description", $$19::get);
                            throw new ReportedException($$21);
                        }
                    }
                }
                if ($$15 < $$12) {
                    IntArraySet intArraySet = new IntArraySet();
                    for (Holder<Biome> $$23 : objectArraySet) {
                        List<HolderSet<PlacedFeature>> $$24 = this.generationSettingsGetter.apply($$23).features();
                        if ($$15 < $$24.size()) {
                            HolderSet<PlacedFeature> $$25 = $$24.get($$15);
                            FeatureSorter.StepFeatureData $$26 = $$8.get($$15);
                            $$25.stream().map((v0) -> {
                                return v0.value();
                            }).forEach($$27 -> {
                                intArraySet.add($$26.indexMapping().applyAsInt($$27));
                            });
                        }
                    }
                    int $$272 = intArraySet.size();
                    int[] $$28 = intArraySet.toIntArray();
                    Arrays.sort($$28);
                    FeatureSorter.StepFeatureData $$29 = $$8.get($$15);
                    for (int $$30 = 0; $$30 < $$272; $$30++) {
                        int $$31 = $$28[$$30];
                        PlacedFeature $$32 = $$29.features().get($$31);
                        Supplier<String> $$33 = () -> {
                            Optional map = $$13.getResourceKey($$32).map((v0) -> {
                                return v0.toString();
                            });
                            Objects.requireNonNull($$32);
                            return (String) map.orElseGet($$32::toString);
                        };
                        $$9.setFeatureSeed($$10, $$31, $$15);
                        try {
                            $$0.setCurrentlyGenerating($$33);
                            $$32.placeWithBiomeCheck($$0, this, $$9, $$5);
                        } catch (Exception $$34) {
                            CrashReport $$35 = CrashReport.forThrowable($$34, "Feature placement");
                            CrashReportCategory crashReportCategoryAddCategory2 = $$35.addCategory("Feature");
                            Objects.requireNonNull($$33);
                            crashReportCategoryAddCategory2.setDetail("Description", $$33::get);
                            throw new ReportedException($$35);
                        }
                    }
                }
            }
            $$0.setCurrentlyGenerating(null);
            if (SharedConstants.DEBUG_FEATURE_COUNT) {
                FeatureCountTracker.chunkDecorated($$0.getLevel());
            }
        } catch (Exception $$36) {
            CrashReport $$37 = CrashReport.forThrowable($$36, "Biome decoration");
            $$37.addCategory("Generation").setDetail("CenterX", Integer.valueOf($$3.x)).setDetail("CenterZ", Integer.valueOf($$3.z)).setDetail("Decoration Seed", Long.valueOf($$10));
            throw new ReportedException($$37);
        }
    }

    private static BoundingBox getWritableArea(ChunkAccess $$0) {
        ChunkPos $$1 = $$0.getPos();
        int $$2 = $$1.getMinBlockX();
        int $$3 = $$1.getMinBlockZ();
        LevelHeightAccessor $$4 = $$0.getHeightAccessorForGeneration();
        int $$5 = $$4.getMinY() + 1;
        int $$6 = $$4.getMaxY();
        return new BoundingBox($$2, $$5, $$3, $$2 + 15, $$6, $$3 + 15);
    }

    public int getSpawnHeight(LevelHeightAccessor $$0) {
        return 64;
    }

    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public WeightedList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<Biome> $$0, StructureManager $$1, MobCategory $$2, BlockPos $$3) {
        Predicate<StructureStart> predicate;
        Map<Structure, LongSet> $$4 = $$1.getAllStructuresAt($$3);
        for (Map.Entry<Structure, LongSet> $$5 : $$4.entrySet()) {
            Structure $$6 = $$5.getKey();
            StructureSpawnOverride $$7 = $$6.spawnOverrides().get($$2);
            if ($$7 != null) {
                MutableBoolean $$8 = new MutableBoolean(false);
                if ($$7.boundingBox() == StructureSpawnOverride.BoundingBoxType.PIECE) {
                    predicate = $$22 -> {
                        return $$1.structureHasPieceAt($$3, $$22);
                    };
                } else {
                    predicate = $$12 -> {
                        return $$12.getBoundingBox().isInside($$3);
                    };
                }
                Predicate<StructureStart> $$9 = predicate;
                $$1.fillStartsForStructure($$6, $$5.getValue(), $$23 -> {
                    if ($$8.isFalse() && $$9.test($$23)) {
                        $$8.setTrue();
                    }
                });
                if ($$8.isTrue()) {
                    return $$7.spawns();
                }
            }
        }
        return $$0.value().getMobSettings().getMobs($$2);
    }

    public void createStructures(RegistryAccess $$0, ChunkGeneratorStructureState $$1, StructureManager $$2, ChunkAccess $$3, StructureTemplateManager $$4, ResourceKey<Level> $$5) {
        if (SharedConstants.DEBUG_DISABLE_STRUCTURES) {
            return;
        }
        ChunkPos $$6 = $$3.getPos();
        SectionPos $$7 = SectionPos.bottomOf($$3);
        RandomState $$8 = $$1.randomState();
        $$1.possibleStructureSets().forEach($$9 -> {
            StructurePlacement $$10 = ((StructureSet) $$9.value()).placement();
            List<StructureSet.StructureSelectionEntry> $$11 = ((StructureSet) $$9.value()).structures();
            for (StructureSet.StructureSelectionEntry $$12 : $$11) {
                StructureStart $$13 = $$2.getStartForStructure($$7, $$12.structure().value(), $$3);
                if ($$13 != null && $$13.isValid()) {
                    return;
                }
            }
            if (!$$10.isStructureChunk($$1, $$6.x, $$6.z)) {
                return;
            }
            if ($$11.size() == 1) {
                tryGenerateStructure($$11.get(0), $$2, $$0, $$8, $$4, $$1.getLevelSeed(), $$3, $$6, $$7, $$5);
                return;
            }
            ArrayList<StructureSet.StructureSelectionEntry> $$14 = new ArrayList<>($$11.size());
            $$14.addAll($$11);
            WorldgenRandom $$15 = new WorldgenRandom(new LegacyRandomSource(0L));
            $$15.setLargeFeatureSeed($$1.getLevelSeed(), $$6.x, $$6.z);
            int $$16 = 0;
            for (StructureSet.StructureSelectionEntry $$17 : $$14) {
                $$16 += $$17.weight();
            }
            while (!$$14.isEmpty()) {
                int $$18 = $$15.nextInt($$16);
                int $$19 = 0;
                for (StructureSet.StructureSelectionEntry $$20 : $$14) {
                    $$18 -= $$20.weight();
                    if ($$18 < 0) {
                        break;
                    } else {
                        $$19++;
                    }
                }
                StructureSet.StructureSelectionEntry $$21 = $$14.get($$19);
                if (tryGenerateStructure($$21, $$2, $$0, $$8, $$4, $$1.getLevelSeed(), $$3, $$6, $$7, $$5)) {
                    return;
                }
                $$14.remove($$19);
                $$16 -= $$21.weight();
            }
        });
    }

    private boolean tryGenerateStructure(StructureSet.StructureSelectionEntry $$0, StructureManager $$1, RegistryAccess $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkAccess $$6, ChunkPos $$7, SectionPos $$8, ResourceKey<Level> $$9) {
        Structure $$10 = $$0.structure().value();
        int $$11 = fetchReferences($$1, $$6, $$8, $$10);
        HolderSet<Biome> $$12 = $$10.biomes();
        Objects.requireNonNull($$12);
        Predicate<Holder<Biome>> $$13 = $$12::contains;
        StructureStart $$14 = $$10.generate($$0.structure(), $$9, $$2, this, this.biomeSource, $$3, $$4, $$5, $$7, $$11, $$6, $$13);
        if ($$14.isValid()) {
            $$1.setStartForStructure($$8, $$10, $$14, $$6);
            return true;
        }
        return false;
    }

    private static int fetchReferences(StructureManager $$0, ChunkAccess $$1, SectionPos $$2, Structure $$3) {
        StructureStart $$4 = $$0.getStartForStructure($$2, $$3, $$1);
        if ($$4 != null) {
            return $$4.getReferences();
        }
        return 0;
    }

    public void createReferences(WorldGenLevel $$0, StructureManager $$1, ChunkAccess $$2) {
        ChunkPos $$4 = $$2.getPos();
        int $$5 = $$4.x;
        int $$6 = $$4.z;
        int $$7 = $$4.getMinBlockX();
        int $$8 = $$4.getMinBlockZ();
        SectionPos $$9 = SectionPos.bottomOf($$2);
        for (int $$10 = $$5 - 8; $$10 <= $$5 + 8; $$10++) {
            for (int $$11 = $$6 - 8; $$11 <= $$6 + 8; $$11++) {
                long $$12 = ChunkPos.asLong($$10, $$11);
                for (StructureStart $$13 : $$0.getChunk($$10, $$11).getAllStarts().values()) {
                    try {
                        if ($$13.isValid() && $$13.getBoundingBox().intersects($$7, $$8, $$7 + 15, $$8 + 15)) {
                            $$1.addReferenceForStructure($$9, $$13.getStructure(), $$12, $$2);
                        }
                    } catch (Exception $$14) {
                        CrashReport $$15 = CrashReport.forThrowable($$14, "Generating structure reference");
                        CrashReportCategory $$16 = $$15.addCategory("Structure");
                        Optional<? extends Registry<Structure>> $$17 = $$0.registryAccess().lookup(Registries.STRUCTURE);
                        $$16.setDetail("Id", () -> {
                            return (String) $$17.map($$18 -> {
                                return $$18.getKey($$13.getStructure()).toString();
                            }).orElse("UNKNOWN");
                        });
                        $$16.setDetail(StateHolder.NAME_TAG, () -> {
                            return BuiltInRegistries.STRUCTURE_TYPE.getKey($$13.getStructure().type()).toString();
                        });
                        $$16.setDetail("Class", () -> {
                            return $$13.getStructure().getClass().getCanonicalName();
                        });
                        throw new ReportedException($$15);
                    }
                }
            }
        }
    }

    public int getFirstFreeHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
        return getBaseHeight($$0, $$1, $$2, $$3, $$4);
    }

    public int getFirstOccupiedHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
        return getBaseHeight($$0, $$1, $$2, $$3, $$4) - 1;
    }

    @Deprecated
    public BiomeGenerationSettings getBiomeGenerationSettings(Holder<Biome> $$0) {
        return this.generationSettingsGetter.apply($$0);
    }
}
