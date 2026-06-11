package net.minecraft.world.level.chunk;

import com.google.common.base.Stopwatch;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/ChunkGeneratorStructureState.class */
public class ChunkGeneratorStructureState {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final RandomState randomState;
    private final BiomeSource biomeSource;
    private final long levelSeed;
    private final long concentricRingsSeed;
    private final Map<Structure, List<StructurePlacement>> placementsForStructure = new Object2ObjectOpenHashMap();
    private final Map<ConcentricRingsStructurePlacement, CompletableFuture<List<ChunkPos>>> ringPositions = new Object2ObjectArrayMap();
    private boolean hasGeneratedPositions;
    private final List<Holder<StructureSet>> possibleStructureSets;

    public static ChunkGeneratorStructureState createForFlat(RandomState $$0, long $$1, BiomeSource $$2, Stream<Holder<StructureSet>> $$3) {
        List<Holder<StructureSet>> $$4 = $$3.filter($$12 -> {
            return hasBiomesForStructureSet((StructureSet) $$12.value(), $$2);
        }).toList();
        return new ChunkGeneratorStructureState($$0, $$2, $$1, 0L, $$4);
    }

    public static ChunkGeneratorStructureState createForNormal(RandomState $$0, long $$1, BiomeSource $$2, HolderLookup<StructureSet> $$3) {
        List<Holder<StructureSet>> $$4 = (List) $$3.listElements().filter($$12 -> {
            return hasBiomesForStructureSet((StructureSet) $$12.value(), $$2);
        }).collect(Collectors.toUnmodifiableList());
        return new ChunkGeneratorStructureState($$0, $$2, $$1, $$1, $$4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasBiomesForStructureSet(StructureSet $$0, BiomeSource $$1) {
        Stream<R> streamFlatMap = $$0.structures().stream().flatMap($$02 -> {
            Structure $$12 = $$02.structure().value();
            return $$12.biomes().stream();
        });
        Set<Holder<Biome>> setPossibleBiomes = $$1.possibleBiomes();
        Objects.requireNonNull(setPossibleBiomes);
        return streamFlatMap.anyMatch((v1) -> {
            return r1.contains(v1);
        });
    }

    private ChunkGeneratorStructureState(RandomState $$0, BiomeSource $$1, long $$2, long $$3, List<Holder<StructureSet>> $$4) {
        this.randomState = $$0;
        this.levelSeed = $$2;
        this.biomeSource = $$1;
        this.concentricRingsSeed = $$3;
        this.possibleStructureSets = $$4;
    }

    public List<Holder<StructureSet>> possibleStructureSets() {
        return this.possibleStructureSets;
    }

    private void generatePositions() {
        Set<Holder<Biome>> $$0 = this.biomeSource.possibleBiomes();
        possibleStructureSets().forEach($$1 -> {
            StructureSet $$2 = (StructureSet) $$1.value();
            boolean $$3 = false;
            for (StructureSet.StructureSelectionEntry $$4 : $$2.structures()) {
                Structure $$5 = $$4.structure().value();
                Stream<Holder<Biome>> stream = $$5.biomes().stream();
                Objects.requireNonNull($$0);
                if (stream.anyMatch((v1) -> {
                    return r1.contains(v1);
                })) {
                    this.placementsForStructure.computeIfAbsent($$5, $$02 -> {
                        return new ArrayList();
                    }).add($$2.placement());
                    $$3 = true;
                }
            }
            if ($$3) {
                StructurePlacement $$6 = $$2.placement();
                if ($$6 instanceof ConcentricRingsStructurePlacement) {
                    ConcentricRingsStructurePlacement $$7 = (ConcentricRingsStructurePlacement) $$6;
                    this.ringPositions.put($$7, generateRingPositions($$1, $$7));
                }
            }
        });
    }

    private CompletableFuture<List<ChunkPos>> generateRingPositions(Holder<StructureSet> $$0, ConcentricRingsStructurePlacement $$1) {
        if ($$1.count() == 0) {
            return CompletableFuture.completedFuture(List.of());
        }
        Stopwatch $$2 = Stopwatch.createStarted(Util.TICKER);
        int $$3 = $$1.distance();
        int $$4 = $$1.count();
        List<CompletableFuture<ChunkPos>> $$5 = new ArrayList<>($$4);
        int $$6 = $$1.spread();
        HolderSet<Biome> $$7 = $$1.preferredBiomes();
        RandomSource $$8 = RandomSource.create();
        $$8.setSeed(this.concentricRingsSeed);
        double $$9 = $$8.nextDouble() * 3.141592653589793d * 2.0d;
        int $$10 = 0;
        int $$11 = 0;
        for (int $$12 = 0; $$12 < $$4; $$12++) {
            double $$13 = ((double) ((4 * $$3) + ($$3 * $$11 * 6))) + (($$8.nextDouble() - 0.5d) * ((double) $$3) * 2.5d);
            int $$14 = (int) Math.round(Math.cos($$9) * $$13);
            int $$15 = (int) Math.round(Math.sin($$9) * $$13);
            RandomSource $$16 = $$8.fork();
            $$5.add(CompletableFuture.supplyAsync(() -> {
                BiomeSource biomeSource = this.biomeSource;
                int iSectionToBlockCoord = SectionPos.sectionToBlockCoord($$14, 8);
                int iSectionToBlockCoord2 = SectionPos.sectionToBlockCoord($$15, 8);
                Objects.requireNonNull($$7);
                Pair<BlockPos, Holder<Biome>> $$42 = biomeSource.findBiomeHorizontal(iSectionToBlockCoord, 0, iSectionToBlockCoord2, 112, $$7::contains, $$16, this.randomState.sampler());
                if ($$42 != null) {
                    BlockPos $$52 = (BlockPos) $$42.getFirst();
                    return new ChunkPos(SectionPos.blockToSectionCoord($$52.getX()), SectionPos.blockToSectionCoord($$52.getZ()));
                }
                return new ChunkPos($$14, $$15);
            }, Util.backgroundExecutor().forName("structureRings")));
            $$9 += 6.283185307179586d / ((double) $$6);
            $$10++;
            if ($$10 == $$6) {
                $$11++;
                $$10 = 0;
                $$6 = Math.min($$6 + ((2 * $$6) / ($$11 + 1)), $$4 - $$12);
                $$9 += $$8.nextDouble() * 3.141592653589793d * 2.0d;
            }
        }
        return Util.sequence($$5).thenApply($$22 -> {
            double $$32 = $$2.stop().elapsed(TimeUnit.MILLISECONDS) / 1000.0d;
            LOGGER.debug("Calculation for {} took {}s", $$0, Double.valueOf($$32));
            return $$22;
        });
    }

    public void ensureStructuresGenerated() {
        if (!this.hasGeneratedPositions) {
            generatePositions();
            this.hasGeneratedPositions = true;
        }
    }

    public List<ChunkPos> getRingPositionsFor(ConcentricRingsStructurePlacement $$0) {
        ensureStructuresGenerated();
        CompletableFuture<List<ChunkPos>> $$1 = this.ringPositions.get($$0);
        if ($$1 != null) {
            return $$1.join();
        }
        return null;
    }

    public List<StructurePlacement> getPlacementsForStructure(Holder<Structure> $$0) {
        ensureStructuresGenerated();
        return this.placementsForStructure.getOrDefault($$0.value(), List.of());
    }

    public RandomState randomState() {
        return this.randomState;
    }

    public boolean hasStructureChunkInRange(Holder<StructureSet> $$0, int $$1, int $$2, int $$3) {
        StructurePlacement $$4 = $$0.value().placement();
        for (int $$5 = $$1 - $$3; $$5 <= $$1 + $$3; $$5++) {
            for (int $$6 = $$2 - $$3; $$6 <= $$2 + $$3; $$6++) {
                if ($$4.isStructureChunk(this, $$5, $$6)) {
                    return true;
                }
            }
        }
        return false;
    }

    public long getLevelSeed() {
        return this.levelSeed;
    }
}
