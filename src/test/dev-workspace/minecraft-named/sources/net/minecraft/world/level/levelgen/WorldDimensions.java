package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.PrimaryLevelData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldDimensions.class */
public final class WorldDimensions extends Record {
    private final Map<ResourceKey<LevelStem>, LevelStem> dimensions;
    public static final MapCodec<WorldDimensions> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.unboundedMap(ResourceKey.codec(Registries.LEVEL_STEM), LevelStem.CODEC).fieldOf("dimensions").forGetter((v0) -> {
            return v0.dimensions();
        })).apply($$0, $$0.stable(WorldDimensions::new));
    });
    private static final Set<ResourceKey<LevelStem>> BUILTIN_ORDER = ImmutableSet.of(LevelStem.OVERWORLD, LevelStem.NETHER, LevelStem.END);
    private static final int VANILLA_DIMENSION_COUNT = BUILTIN_ORDER.size();

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldDimensions.class), WorldDimensions.class, "dimensions", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions;->dimensions:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldDimensions.class), WorldDimensions.class, "dimensions", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions;->dimensions:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldDimensions.class, Object.class), WorldDimensions.class, "dimensions", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions;->dimensions:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<ResourceKey<LevelStem>, LevelStem> dimensions() {
        return this.dimensions;
    }

    public WorldDimensions(Map<ResourceKey<LevelStem>, LevelStem> $$0) {
        LevelStem $$1 = $$0.get(LevelStem.OVERWORLD);
        if ($$1 != null) {
            this.dimensions = $$0;
            return;
        }
        throw new IllegalStateException("Overworld settings missing");
    }

    public WorldDimensions(Registry<LevelStem> $$0) {
        this((Map<ResourceKey<LevelStem>, LevelStem>) $$0.listElements().collect(Collectors.toMap((v0) -> {
            return v0.key();
        }, (v0) -> {
            return v0.value();
        })));
    }

    public static Stream<ResourceKey<LevelStem>> keysInOrder(Stream<ResourceKey<LevelStem>> $$0) {
        return Stream.concat(BUILTIN_ORDER.stream(), $$0.filter($$02 -> {
            return !BUILTIN_ORDER.contains($$02);
        }));
    }

    public WorldDimensions replaceOverworldGenerator(HolderLookup.Provider $$0, ChunkGenerator $$1) {
        HolderLookup<DimensionType> $$2 = $$0.lookupOrThrow((ResourceKey) Registries.DIMENSION_TYPE);
        Map<ResourceKey<LevelStem>, LevelStem> $$3 = withOverworld($$2, this.dimensions, $$1);
        return new WorldDimensions($$3);
    }

    public static Map<ResourceKey<LevelStem>, LevelStem> withOverworld(HolderLookup<DimensionType> $$0, Map<ResourceKey<LevelStem>, LevelStem> $$1, ChunkGenerator $$2) {
        LevelStem $$3 = $$1.get(LevelStem.OVERWORLD);
        Holder<DimensionType> $$4 = $$3 == null ? $$0.getOrThrow(BuiltinDimensionTypes.OVERWORLD) : $$3.type();
        return withOverworld($$1, $$4, $$2);
    }

    public static Map<ResourceKey<LevelStem>, LevelStem> withOverworld(Map<ResourceKey<LevelStem>, LevelStem> $$0, Holder<DimensionType> $$1, ChunkGenerator $$2) {
        ImmutableMap.Builder<ResourceKey<LevelStem>, LevelStem> $$3 = ImmutableMap.builder();
        $$3.putAll($$0);
        $$3.put(LevelStem.OVERWORLD, new LevelStem($$1, $$2));
        return $$3.buildKeepingLast();
    }

    public ChunkGenerator overworld() {
        LevelStem $$0 = this.dimensions.get(LevelStem.OVERWORLD);
        if ($$0 == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
        return $$0.generator();
    }

    public Optional<LevelStem> get(ResourceKey<LevelStem> $$0) {
        return Optional.ofNullable(this.dimensions.get($$0));
    }

    public ImmutableSet<ResourceKey<Level>> levels() {
        return (ImmutableSet) dimensions().keySet().stream().map(Registries::levelStemToLevel).collect(ImmutableSet.toImmutableSet());
    }

    public boolean isDebug() {
        return overworld() instanceof DebugLevelSource;
    }

    private static PrimaryLevelData.SpecialWorldProperty specialWorldProperty(Registry<LevelStem> $$0) {
        return (PrimaryLevelData.SpecialWorldProperty) $$0.getOptional(LevelStem.OVERWORLD).map($$02 -> {
            ChunkGenerator $$1 = $$02.generator();
            if ($$1 instanceof DebugLevelSource) {
                return PrimaryLevelData.SpecialWorldProperty.DEBUG;
            }
            if ($$1 instanceof FlatLevelSource) {
                return PrimaryLevelData.SpecialWorldProperty.FLAT;
            }
            return PrimaryLevelData.SpecialWorldProperty.NONE;
        }).orElse(PrimaryLevelData.SpecialWorldProperty.NONE);
    }

    static Lifecycle checkStability(ResourceKey<LevelStem> $$0, LevelStem $$1) {
        return isVanillaLike($$0, $$1) ? Lifecycle.stable() : Lifecycle.experimental();
    }

    private static boolean isVanillaLike(ResourceKey<LevelStem> $$0, LevelStem $$1) {
        if ($$0 == LevelStem.OVERWORLD) {
            return isStableOverworld($$1);
        }
        if ($$0 == LevelStem.NETHER) {
            return isStableNether($$1);
        }
        if ($$0 == LevelStem.END) {
            return isStableEnd($$1);
        }
        return false;
    }

    private static boolean isStableOverworld(LevelStem $$0) {
        Holder<DimensionType> $$1 = $$0.type();
        if (!$$1.is(BuiltinDimensionTypes.OVERWORLD) && !$$1.is(BuiltinDimensionTypes.OVERWORLD_CAVES)) {
            return false;
        }
        BiomeSource biomeSource = $$0.generator().getBiomeSource();
        if (biomeSource instanceof MultiNoiseBiomeSource) {
            MultiNoiseBiomeSource $$2 = (MultiNoiseBiomeSource) biomeSource;
            if (!$$2.stable(MultiNoiseBiomeSourceParameterLists.OVERWORLD)) {
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean isStableNether(LevelStem $$0) {
        if ($$0.type().is(BuiltinDimensionTypes.NETHER)) {
            ChunkGenerator chunkGeneratorGenerator = $$0.generator();
            if (chunkGeneratorGenerator instanceof NoiseBasedChunkGenerator) {
                NoiseBasedChunkGenerator $$1 = (NoiseBasedChunkGenerator) chunkGeneratorGenerator;
                if ($$1.stable(NoiseGeneratorSettings.NETHER)) {
                    BiomeSource biomeSource = $$1.getBiomeSource();
                    if (biomeSource instanceof MultiNoiseBiomeSource) {
                        MultiNoiseBiomeSource $$2 = (MultiNoiseBiomeSource) biomeSource;
                        if ($$2.stable(MultiNoiseBiomeSourceParameterLists.NETHER)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isStableEnd(LevelStem $$0) {
        if ($$0.type().is(BuiltinDimensionTypes.END)) {
            ChunkGenerator chunkGeneratorGenerator = $$0.generator();
            if (chunkGeneratorGenerator instanceof NoiseBasedChunkGenerator) {
                NoiseBasedChunkGenerator $$1 = (NoiseBasedChunkGenerator) chunkGeneratorGenerator;
                if ($$1.stable(NoiseGeneratorSettings.END) && ($$1.getBiomeSource() instanceof TheEndBiomeSource)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: net.minecraft.world.level.levelgen.WorldDimensions$1Entry, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldDimensions$1Entry.class */
    static final class C1Entry extends Record {
        private final ResourceKey<LevelStem> key;
        private final LevelStem value;

        C1Entry(ResourceKey<LevelStem> $$0, LevelStem $$1) {
            this.key = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, C1Entry.class), C1Entry.class, "key;value", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->value:Lnet/minecraft/world/level/dimension/LevelStem;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, C1Entry.class), C1Entry.class, "key;value", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->value:Lnet/minecraft/world/level/dimension/LevelStem;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, C1Entry.class, Object.class), C1Entry.class, "key;value", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->key:Lnet/minecraft/resources/ResourceKey;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$1Entry;->value:Lnet/minecraft/world/level/dimension/LevelStem;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<LevelStem> key() {
            return this.key;
        }

        public LevelStem value() {
            return this.value;
        }

        RegistrationInfo registrationInfo() {
            return new RegistrationInfo(Optional.empty(), WorldDimensions.checkStability(this.key, this.value));
        }
    }

    public Complete bake(Registry<LevelStem> $$0) {
        Stream<ResourceKey<LevelStem>> $$1 = Stream.concat($$0.registryKeySet().stream(), this.dimensions.keySet().stream()).distinct();
        List<C1Entry> $$2 = new ArrayList<>();
        keysInOrder($$1).forEach($$22 -> {
            $$0.getOptional($$22).or(() -> {
                return Optional.ofNullable(this.dimensions.get($$22));
            }).ifPresent($$22 -> {
                $$2.add(new C1Entry($$22, $$22));
            });
        });
        Lifecycle $$3 = $$2.size() == VANILLA_DIMENSION_COUNT ? Lifecycle.stable() : Lifecycle.experimental();
        WritableRegistry<LevelStem> $$4 = new MappedRegistry<>(Registries.LEVEL_STEM, $$3);
        $$2.forEach($$12 -> {
            $$4.register($$12.key, $$12.value, $$12.registrationInfo());
        });
        Registry<LevelStem> $$5 = $$4.freeze();
        PrimaryLevelData.SpecialWorldProperty $$6 = specialWorldProperty($$5);
        return new Complete($$5.freeze(), $$6);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/WorldDimensions$Complete.class */
    public static final class Complete extends Record {
        private final Registry<LevelStem> dimensions;
        private final PrimaryLevelData.SpecialWorldProperty specialWorldProperty;

        public Complete(Registry<LevelStem> $$0, PrimaryLevelData.SpecialWorldProperty $$1) {
            this.dimensions = $$0;
            this.specialWorldProperty = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Complete.class), Complete.class, "dimensions;specialWorldProperty", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->dimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->specialWorldProperty:Lnet/minecraft/world/level/storage/PrimaryLevelData$SpecialWorldProperty;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Complete.class), Complete.class, "dimensions;specialWorldProperty", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->dimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->specialWorldProperty:Lnet/minecraft/world/level/storage/PrimaryLevelData$SpecialWorldProperty;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Complete.class, Object.class), Complete.class, "dimensions;specialWorldProperty", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->dimensions:Lnet/minecraft/core/Registry;", "FIELD:Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;->specialWorldProperty:Lnet/minecraft/world/level/storage/PrimaryLevelData$SpecialWorldProperty;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Registry<LevelStem> dimensions() {
            return this.dimensions;
        }

        public PrimaryLevelData.SpecialWorldProperty specialWorldProperty() {
            return this.specialWorldProperty;
        }

        public Lifecycle lifecycle() {
            return this.dimensions.registryLifecycle();
        }

        public RegistryAccess.Frozen dimensionsRegistryAccess() {
            return new RegistryAccess.ImmutableRegistryAccess((List<? extends Registry<?>>) List.of(this.dimensions)).freeze();
        }
    }
}
