package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.QuartPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/Structure.class */
public abstract class Structure {
    public static final Codec<Structure> DIRECT_CODEC = BuiltInRegistries.STRUCTURE_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    public static final Codec<Holder<Structure>> CODEC = RegistryFileCodec.create(Registries.STRUCTURE, DIRECT_CODEC);
    protected final StructureSettings settings;

    protected abstract Optional<GenerationStub> findGenerationPoint(GenerationContext generationContext);

    public abstract StructureType<?> type();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/Structure$StructureSettings.class */
    public static final class StructureSettings extends Record {
        private final HolderSet<Biome> biomes;
        private final Map<MobCategory, StructureSpawnOverride> spawnOverrides;
        private final GenerationStep.Decoration step;
        private final TerrainAdjustment terrainAdaptation;
        static final StructureSettings DEFAULT = new StructureSettings(HolderSet.direct(new Holder[0]), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE);
        public static final MapCodec<StructureSettings> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter((v0) -> {
                return v0.biomes();
            }), Codec.simpleMap(MobCategory.CODEC, StructureSpawnOverride.CODEC, StringRepresentable.keys(MobCategory.values())).fieldOf("spawn_overrides").forGetter((v0) -> {
                return v0.spawnOverrides();
            }), GenerationStep.Decoration.CODEC.fieldOf("step").forGetter((v0) -> {
                return v0.step();
            }), TerrainAdjustment.CODEC.optionalFieldOf("terrain_adaptation", DEFAULT.terrainAdaptation).forGetter((v0) -> {
                return v0.terrainAdaptation();
            })).apply($$0, StructureSettings::new);
        });

        public StructureSettings(HolderSet<Biome> $$0, Map<MobCategory, StructureSpawnOverride> $$1, GenerationStep.Decoration $$2, TerrainAdjustment $$3) {
            this.biomes = $$0;
            this.spawnOverrides = $$1;
            this.step = $$2;
            this.terrainAdaptation = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StructureSettings.class), StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->biomes:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->spawnOverrides:Ljava/util/Map;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->step:Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->terrainAdaptation:Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StructureSettings.class), StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->biomes:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->spawnOverrides:Ljava/util/Map;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->step:Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->terrainAdaptation:Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StructureSettings.class, Object.class), StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->biomes:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->spawnOverrides:Ljava/util/Map;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->step:Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;->terrainAdaptation:Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public HolderSet<Biome> biomes() {
            return this.biomes;
        }

        public Map<MobCategory, StructureSpawnOverride> spawnOverrides() {
            return this.spawnOverrides;
        }

        public GenerationStep.Decoration step() {
            return this.step;
        }

        public TerrainAdjustment terrainAdaptation() {
            return this.terrainAdaptation;
        }

        public StructureSettings(HolderSet<Biome> $$0) {
            this($$0, DEFAULT.spawnOverrides, DEFAULT.step, DEFAULT.terrainAdaptation);
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/Structure$StructureSettings$Builder.class */
        public static class Builder {
            private final HolderSet<Biome> biomes;
            private Map<MobCategory, StructureSpawnOverride> spawnOverrides = StructureSettings.DEFAULT.spawnOverrides;
            private GenerationStep.Decoration step = StructureSettings.DEFAULT.step;
            private TerrainAdjustment terrainAdaption = StructureSettings.DEFAULT.terrainAdaptation;

            public Builder(HolderSet<Biome> $$0) {
                this.biomes = $$0;
            }

            public Builder spawnOverrides(Map<MobCategory, StructureSpawnOverride> $$0) {
                this.spawnOverrides = $$0;
                return this;
            }

            public Builder generationStep(GenerationStep.Decoration $$0) {
                this.step = $$0;
                return this;
            }

            public Builder terrainAdapation(TerrainAdjustment $$0) {
                this.terrainAdaption = $$0;
                return this;
            }

            public StructureSettings build() {
                return new StructureSettings(this.biomes, this.spawnOverrides, this.step, this.terrainAdaption);
            }
        }
    }

    public static <S extends Structure> RecordCodecBuilder<S, StructureSettings> settingsCodec(RecordCodecBuilder.Instance<S> $$0) {
        return StructureSettings.CODEC.forGetter($$02 -> {
            return $$02.settings;
        });
    }

    public static <S extends Structure> MapCodec<S> simpleCodec(Function<StructureSettings, S> $$0) {
        return RecordCodecBuilder.mapCodec($$1 -> {
            return $$1.group(settingsCodec($$1)).apply($$1, $$0);
        });
    }

    protected Structure(StructureSettings $$0) {
        this.settings = $$0;
    }

    public HolderSet<Biome> biomes() {
        return this.settings.biomes;
    }

    public Map<MobCategory, StructureSpawnOverride> spawnOverrides() {
        return this.settings.spawnOverrides;
    }

    public GenerationStep.Decoration step() {
        return this.settings.step;
    }

    public TerrainAdjustment terrainAdaptation() {
        return this.settings.terrainAdaptation;
    }

    public BoundingBox adjustBoundingBox(BoundingBox $$0) {
        if (terrainAdaptation() != TerrainAdjustment.NONE) {
            return $$0.inflatedBy(12);
        }
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/Structure$GenerationContext.class */
    public static final class GenerationContext extends Record {
        private final RegistryAccess registryAccess;
        private final ChunkGenerator chunkGenerator;
        private final BiomeSource biomeSource;
        private final RandomState randomState;
        private final StructureTemplateManager structureTemplateManager;
        private final WorldgenRandom random;
        private final long seed;
        private final ChunkPos chunkPos;
        private final LevelHeightAccessor heightAccessor;
        private final Predicate<Holder<Biome>> validBiome;

        public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, WorldgenRandom $$5, long $$6, ChunkPos $$7, LevelHeightAccessor $$8, Predicate<Holder<Biome>> $$9) {
            this.registryAccess = $$0;
            this.chunkGenerator = $$1;
            this.biomeSource = $$2;
            this.randomState = $$3;
            this.structureTemplateManager = $$4;
            this.random = $$5;
            this.seed = $$6;
            this.chunkPos = $$7;
            this.heightAccessor = $$8;
            this.validBiome = $$9;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GenerationContext.class), GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->registryAccess:Lnet/minecraft/core/RegistryAccess;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkGenerator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->biomeSource:Lnet/minecraft/world/level/biome/BiomeSource;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->randomState:Lnet/minecraft/world/level/levelgen/RandomState;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->structureTemplateManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->random:Lnet/minecraft/world/level/levelgen/WorldgenRandom;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->seed:J", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->heightAccessor:Lnet/minecraft/world/level/LevelHeightAccessor;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->validBiome:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GenerationContext.class), GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->registryAccess:Lnet/minecraft/core/RegistryAccess;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkGenerator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->biomeSource:Lnet/minecraft/world/level/biome/BiomeSource;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->randomState:Lnet/minecraft/world/level/levelgen/RandomState;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->structureTemplateManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->random:Lnet/minecraft/world/level/levelgen/WorldgenRandom;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->seed:J", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->heightAccessor:Lnet/minecraft/world/level/LevelHeightAccessor;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->validBiome:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GenerationContext.class, Object.class), GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->registryAccess:Lnet/minecraft/core/RegistryAccess;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkGenerator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->biomeSource:Lnet/minecraft/world/level/biome/BiomeSource;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->randomState:Lnet/minecraft/world/level/levelgen/RandomState;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->structureTemplateManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->random:Lnet/minecraft/world/level/levelgen/WorldgenRandom;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->seed:J", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->chunkPos:Lnet/minecraft/world/level/ChunkPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->heightAccessor:Lnet/minecraft/world/level/LevelHeightAccessor;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;->validBiome:Ljava/util/function/Predicate;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public RegistryAccess registryAccess() {
            return this.registryAccess;
        }

        public ChunkGenerator chunkGenerator() {
            return this.chunkGenerator;
        }

        public BiomeSource biomeSource() {
            return this.biomeSource;
        }

        public RandomState randomState() {
            return this.randomState;
        }

        public StructureTemplateManager structureTemplateManager() {
            return this.structureTemplateManager;
        }

        public WorldgenRandom random() {
            return this.random;
        }

        public long seed() {
            return this.seed;
        }

        public ChunkPos chunkPos() {
            return this.chunkPos;
        }

        public LevelHeightAccessor heightAccessor() {
            return this.heightAccessor;
        }

        public Predicate<Holder<Biome>> validBiome() {
            return this.validBiome;
        }

        public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkPos $$6, LevelHeightAccessor $$7, Predicate<Holder<Biome>> $$8) {
            this($$0, $$1, $$2, $$3, $$4, makeRandom($$5, $$6), $$5, $$6, $$7, $$8);
        }

        private static WorldgenRandom makeRandom(long $$0, ChunkPos $$1) {
            WorldgenRandom $$2 = new WorldgenRandom(new LegacyRandomSource(0L));
            $$2.setLargeFeatureSeed($$0, $$1.x, $$1.z);
            return $$2;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/Structure$GenerationStub.class */
    public static final class GenerationStub extends Record {
        private final BlockPos position;
        private final Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> generator;

        public GenerationStub(BlockPos $$0, Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> $$1) {
            this.position = $$0;
            this.generator = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GenerationStub.class), GenerationStub.class, "position;generator", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->position:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->generator:Lcom/mojang/datafixers/util/Either;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GenerationStub.class), GenerationStub.class, "position;generator", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->position:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->generator:Lcom/mojang/datafixers/util/Either;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GenerationStub.class, Object.class), GenerationStub.class, "position;generator", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->position:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;->generator:Lcom/mojang/datafixers/util/Either;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BlockPos position() {
            return this.position;
        }

        public Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> generator() {
            return this.generator;
        }

        public GenerationStub(BlockPos $$0, Consumer<StructurePiecesBuilder> $$1) {
            this($$0, (Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder>) Either.left($$1));
        }

        public StructurePiecesBuilder getPiecesBuilder() {
            return (StructurePiecesBuilder) this.generator.map($$0 -> {
                StructurePiecesBuilder $$1 = new StructurePiecesBuilder();
                $$0.accept($$1);
                return $$1;
            }, $$02 -> {
                return $$02;
            });
        }
    }

    public StructureStart generate(Holder<Structure> $$0, ResourceKey<Level> $$1, RegistryAccess $$2, ChunkGenerator $$3, BiomeSource $$4, RandomState $$5, StructureTemplateManager $$6, long $$7, ChunkPos $$8, int $$9, LevelHeightAccessor $$10, Predicate<Holder<Biome>> $$11) {
        ProfiledDuration $$12 = JvmProfiler.INSTANCE.onStructureGenerate($$8, $$1, $$0);
        GenerationContext $$13 = new GenerationContext($$2, $$3, $$4, $$5, $$6, $$7, $$8, $$10, $$11);
        Optional<GenerationStub> $$14 = findValidGenerationPoint($$13);
        if ($$14.isPresent()) {
            StructurePiecesBuilder $$15 = $$14.get().getPiecesBuilder();
            StructureStart $$16 = new StructureStart(this, $$8, $$9, $$15.build());
            if ($$16.isValid()) {
                if ($$12 != null) {
                    $$12.finish(true);
                }
                return $$16;
            }
        }
        if ($$12 != null) {
            $$12.finish(false);
        }
        return StructureStart.INVALID_START;
    }

    protected static Optional<GenerationStub> onTopOfChunkCenter(GenerationContext $$0, Heightmap.Types $$1, Consumer<StructurePiecesBuilder> $$2) {
        ChunkPos $$3 = $$0.chunkPos();
        int $$4 = $$3.getMiddleBlockX();
        int $$5 = $$3.getMiddleBlockZ();
        int $$6 = $$0.chunkGenerator().getFirstOccupiedHeight($$4, $$5, $$1, $$0.heightAccessor(), $$0.randomState());
        return Optional.of(new GenerationStub(new BlockPos($$4, $$6, $$5), $$2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidBiome(GenerationStub $$0, GenerationContext $$1) {
        BlockPos $$2 = $$0.position();
        return $$1.validBiome.test($$1.chunkGenerator.getBiomeSource().getNoiseBiome(QuartPos.fromBlock($$2.getX()), QuartPos.fromBlock($$2.getY()), QuartPos.fromBlock($$2.getZ()), $$1.randomState.sampler()));
    }

    public void afterPlace(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, PiecesContainer $$6) {
    }

    private static int[] getCornerHeights(GenerationContext $$0, int $$1, int $$2, int $$3, int $$4) {
        ChunkGenerator $$5 = $$0.chunkGenerator();
        LevelHeightAccessor $$6 = $$0.heightAccessor();
        RandomState $$7 = $$0.randomState();
        return new int[]{$$5.getFirstOccupiedHeight($$1, $$3, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.getFirstOccupiedHeight($$1, $$3 + $$4, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.getFirstOccupiedHeight($$1 + $$2, $$3, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.getFirstOccupiedHeight($$1 + $$2, $$3 + $$4, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7)};
    }

    public static int getMeanFirstOccupiedHeight(GenerationContext $$0, int $$1, int $$2, int $$3, int $$4) {
        int[] $$5 = getCornerHeights($$0, $$1, $$2, $$3, $$4);
        return ((($$5[0] + $$5[1]) + $$5[2]) + $$5[3]) / 4;
    }

    protected static int getLowestY(GenerationContext $$0, int $$1, int $$2) {
        ChunkPos $$3 = $$0.chunkPos();
        int $$4 = $$3.getMinBlockX();
        int $$5 = $$3.getMinBlockZ();
        return getLowestY($$0, $$4, $$5, $$1, $$2);
    }

    protected static int getLowestY(GenerationContext $$0, int $$1, int $$2, int $$3, int $$4) {
        int[] $$5 = getCornerHeights($$0, $$1, $$3, $$2, $$4);
        return Math.min(Math.min($$5[0], $$5[1]), Math.min($$5[2], $$5[3]));
    }

    @Deprecated
    protected BlockPos getLowestYIn5by5BoxOffset7Blocks(GenerationContext $$0, Rotation $$1) {
        int $$2 = 5;
        int $$3 = 5;
        if ($$1 == Rotation.CLOCKWISE_90) {
            $$2 = -5;
        } else if ($$1 == Rotation.CLOCKWISE_180) {
            $$2 = -5;
            $$3 = -5;
        } else if ($$1 == Rotation.COUNTERCLOCKWISE_90) {
            $$3 = -5;
        }
        ChunkPos $$4 = $$0.chunkPos();
        int $$5 = $$4.getBlockX(7);
        int $$6 = $$4.getBlockZ(7);
        return new BlockPos($$5, getLowestY($$0, $$5, $$6, $$2, $$3), $$6);
    }

    public Optional<GenerationStub> findValidGenerationPoint(GenerationContext $$0) {
        return findGenerationPoint($$0).filter($$1 -> {
            return isValidBiome($$1, $$0);
        });
    }
}
