package net.minecraft.world.level.levelgen;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk.class */
public class NoiseChunk implements DensityFunction.ContextProvider, DensityFunction.FunctionContext {
    final int cellCountXZ;
    final int cellCountY;
    final int cellNoiseMinY;
    private final int firstCellX;
    private final int firstCellZ;
    final int firstNoiseX;
    final int firstNoiseZ;
    private final Aquifer aquifer;
    private final DensityFunction preliminarySurfaceLevel;
    private final BlockStateFiller blockStateRule;
    private final Blender blender;
    private final DensityFunctions.BeardifierOrMarker beardifier;
    final int noiseSizeXZ;
    final int cellWidth;
    final int cellHeight;
    boolean interpolating;
    boolean fillingCell;
    private int cellStartBlockX;
    int cellStartBlockY;
    private int cellStartBlockZ;
    int inCellX;
    int inCellY;
    int inCellZ;
    long interpolationCounter;
    long arrayInterpolationCounter;
    int arrayIndex;
    private final Map<DensityFunction, DensityFunction> wrapped = new HashMap();
    private final Long2IntMap preliminarySurfaceLevelCache = new Long2IntOpenHashMap();
    private long lastBlendingDataPos = ChunkPos.INVALID_CHUNK_POS;
    private Blender.BlendingOutput lastBlendingOutput = new Blender.BlendingOutput(1.0d, Density.SURFACE);
    private final DensityFunction.ContextProvider sliceFillingContextProvider = new DensityFunction.ContextProvider() { // from class: net.minecraft.world.level.levelgen.NoiseChunk.1
        @Override // net.minecraft.world.level.levelgen.DensityFunction.ContextProvider
        public DensityFunction.FunctionContext forIndex(int $$0) {
            NoiseChunk.this.cellStartBlockY = ($$0 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
            NoiseChunk.this.interpolationCounter++;
            NoiseChunk.this.inCellY = 0;
            NoiseChunk.this.arrayIndex = $$0;
            return NoiseChunk.this;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction.ContextProvider
        public void fillAllDirectly(double[] $$0, DensityFunction $$1) {
            for (int $$2 = 0; $$2 < NoiseChunk.this.cellCountY + 1; $$2++) {
                NoiseChunk.this.cellStartBlockY = ($$2 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
                NoiseChunk.this.interpolationCounter++;
                NoiseChunk.this.inCellY = 0;
                NoiseChunk.this.arrayIndex = $$2;
                $$0[$$2] = $$1.compute(NoiseChunk.this);
            }
        }
    };
    final List<NoiseInterpolator> interpolators = Lists.newArrayList();
    final List<CacheAllInCell> cellCaches = Lists.newArrayList();
    private final FlatCache blendAlpha = new FlatCache(new BlendAlpha(), false);
    private final FlatCache blendOffset = new FlatCache(new BlendOffset(), false);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$BlockStateFiller.class */
    @FunctionalInterface
    public interface BlockStateFiller {
        BlockState calculate(DensityFunction.FunctionContext functionContext);
    }

    public static NoiseChunk forChunk(ChunkAccess $$0, RandomState $$1, DensityFunctions.BeardifierOrMarker $$2, NoiseGeneratorSettings $$3, Aquifer.FluidPicker $$4, Blender $$5) {
        NoiseSettings $$6 = $$3.noiseSettings().clampToHeightAccessor($$0);
        ChunkPos $$7 = $$0.getPos();
        int $$8 = 16 / $$6.getCellWidth();
        return new NoiseChunk($$8, $$1, $$7.getMinBlockX(), $$7.getMinBlockZ(), $$6, $$2, $$3, $$4, $$5);
    }

    public NoiseChunk(int $$0, RandomState $$1, int $$2, int $$3, NoiseSettings $$4, DensityFunctions.BeardifierOrMarker $$5, NoiseGeneratorSettings $$6, Aquifer.FluidPicker $$7, Blender $$8) {
        this.cellWidth = $$4.getCellWidth();
        this.cellHeight = $$4.getCellHeight();
        this.cellCountXZ = $$0;
        this.cellCountY = Mth.floorDiv($$4.height(), this.cellHeight);
        this.cellNoiseMinY = Mth.floorDiv($$4.minY(), this.cellHeight);
        this.firstCellX = Math.floorDiv($$2, this.cellWidth);
        this.firstCellZ = Math.floorDiv($$3, this.cellWidth);
        this.firstNoiseX = QuartPos.fromBlock($$2);
        this.firstNoiseZ = QuartPos.fromBlock($$3);
        this.noiseSizeXZ = QuartPos.fromBlock($$0 * this.cellWidth);
        this.blender = $$8;
        this.beardifier = $$5;
        if (!$$8.isEmpty()) {
            for (int $$9 = 0; $$9 <= this.noiseSizeXZ; $$9++) {
                int $$10 = this.firstNoiseX + $$9;
                int $$11 = QuartPos.toBlock($$10);
                for (int $$12 = 0; $$12 <= this.noiseSizeXZ; $$12++) {
                    int $$13 = this.firstNoiseZ + $$12;
                    int $$14 = QuartPos.toBlock($$13);
                    Blender.BlendingOutput $$15 = $$8.blendOffsetAndFactor($$11, $$14);
                    this.blendAlpha.values[$$9 + ($$12 * this.blendAlpha.sizeXZ)] = $$15.alpha();
                    this.blendOffset.values[$$9 + ($$12 * this.blendOffset.sizeXZ)] = $$15.blendingOffset();
                }
            }
        } else {
            Arrays.fill(this.blendAlpha.values, 1.0d);
            Arrays.fill(this.blendOffset.values, Density.SURFACE);
        }
        NoiseRouter $$16 = $$1.router();
        NoiseRouter $$17 = $$16.mapAll(this::wrap);
        this.preliminarySurfaceLevel = $$17.preliminarySurfaceLevel();
        if (!$$6.isAquifersEnabled()) {
            this.aquifer = Aquifer.createDisabled($$7);
        } else {
            int $$18 = SectionPos.blockToSectionCoord($$2);
            int $$19 = SectionPos.blockToSectionCoord($$3);
            this.aquifer = Aquifer.create(this, new ChunkPos($$18, $$19), $$17, $$1.aquiferRandom(), $$4.minY(), $$4.height(), $$7);
        }
        List<BlockStateFiller> $$20 = new ArrayList<>();
        DensityFunction $$21 = DensityFunctions.cacheAllInCell(DensityFunctions.add($$17.finalDensity(), DensityFunctions.BeardifierMarker.INSTANCE)).mapAll(this::wrap);
        $$20.add($$110 -> {
            return this.aquifer.computeSubstance($$110, $$21.compute($$110));
        });
        if ($$6.oreVeinsEnabled()) {
            $$20.add(OreVeinifier.create($$17.veinToggle(), $$17.veinRidged(), $$17.veinGap(), $$1.oreRandom()));
        }
        this.blockStateRule = new MaterialRuleList((BlockStateFiller[]) $$20.toArray(new BlockStateFiller[0]));
    }

    protected Climate.Sampler cachedClimateSampler(NoiseRouter $$0, List<Climate.ParameterPoint> $$1) {
        return new Climate.Sampler($$0.temperature().mapAll(this::wrap), $$0.vegetation().mapAll(this::wrap), $$0.continents().mapAll(this::wrap), $$0.erosion().mapAll(this::wrap), $$0.depth().mapAll(this::wrap), $$0.ridges().mapAll(this::wrap), $$1);
    }

    protected BlockState getInterpolatedState() {
        return this.blockStateRule.calculate(this);
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
    public int blockX() {
        return this.cellStartBlockX + this.inCellX;
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
    public int blockY() {
        return this.cellStartBlockY + this.inCellY;
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
    public int blockZ() {
        return this.cellStartBlockZ + this.inCellZ;
    }

    public int maxPreliminarySurfaceLevel(int $$0, int $$1, int $$2, int $$3) {
        int $$4 = Integer.MIN_VALUE;
        for (int $$5 = $$1; $$5 <= $$3; $$5 += 4) {
            for (int $$6 = $$0; $$6 <= $$2; $$6 += 4) {
                int $$7 = preliminarySurfaceLevel($$6, $$5);
                if ($$7 > $$4) {
                    $$4 = $$7;
                }
            }
        }
        return $$4;
    }

    public int preliminarySurfaceLevel(int $$0, int $$1) {
        int $$2 = QuartPos.toBlock(QuartPos.fromBlock($$0));
        int $$3 = QuartPos.toBlock(QuartPos.fromBlock($$1));
        return this.preliminarySurfaceLevelCache.computeIfAbsent(ColumnPos.asLong($$2, $$3), this::computePreliminarySurfaceLevel);
    }

    private int computePreliminarySurfaceLevel(long $$0) {
        int $$1 = ColumnPos.getX($$0);
        int $$2 = ColumnPos.getZ($$0);
        return Mth.floor(this.preliminarySurfaceLevel.compute(new DensityFunction.SinglePointContext($$1, 0, $$2)));
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.FunctionContext
    public Blender getBlender() {
        return this.blender;
    }

    private void fillSlice(boolean $$0, int $$1) {
        this.cellStartBlockX = $$1 * this.cellWidth;
        this.inCellX = 0;
        for (int $$2 = 0; $$2 < this.cellCountXZ + 1; $$2++) {
            int $$3 = this.firstCellZ + $$2;
            this.cellStartBlockZ = $$3 * this.cellWidth;
            this.inCellZ = 0;
            this.arrayInterpolationCounter++;
            for (NoiseInterpolator $$4 : this.interpolators) {
                double[] $$5 = ($$0 ? $$4.slice0 : $$4.slice1)[$$2];
                $$4.fillArray($$5, this.sliceFillingContextProvider);
            }
        }
        this.arrayInterpolationCounter++;
    }

    public void initializeForFirstCellX() {
        if (this.interpolating) {
            throw new IllegalStateException("Staring interpolation twice");
        }
        this.interpolating = true;
        this.interpolationCounter = 0L;
        fillSlice(true, this.firstCellX);
    }

    public void advanceCellX(int $$0) {
        fillSlice(false, this.firstCellX + $$0 + 1);
        this.cellStartBlockX = (this.firstCellX + $$0) * this.cellWidth;
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.ContextProvider
    public NoiseChunk forIndex(int $$0) {
        int $$1 = Math.floorMod($$0, this.cellWidth);
        int $$2 = Math.floorDiv($$0, this.cellWidth);
        int $$3 = Math.floorMod($$2, this.cellWidth);
        int $$4 = (this.cellHeight - 1) - Math.floorDiv($$2, this.cellWidth);
        this.inCellX = $$3;
        this.inCellY = $$4;
        this.inCellZ = $$1;
        this.arrayIndex = $$0;
        return this;
    }

    @Override // net.minecraft.world.level.levelgen.DensityFunction.ContextProvider
    public void fillAllDirectly(double[] $$0, DensityFunction $$1) {
        this.arrayIndex = 0;
        for (int $$2 = this.cellHeight - 1; $$2 >= 0; $$2--) {
            this.inCellY = $$2;
            for (int $$3 = 0; $$3 < this.cellWidth; $$3++) {
                this.inCellX = $$3;
                for (int $$4 = 0; $$4 < this.cellWidth; $$4++) {
                    this.inCellZ = $$4;
                    int i = this.arrayIndex;
                    this.arrayIndex = i + 1;
                    $$0[i] = $$1.compute(this);
                }
            }
        }
    }

    public void selectCellYZ(int $$0, int $$1) {
        for (NoiseInterpolator $$2 : this.interpolators) {
            $$2.selectCellYZ($$0, $$1);
        }
        this.fillingCell = true;
        this.cellStartBlockY = ($$0 + this.cellNoiseMinY) * this.cellHeight;
        this.cellStartBlockZ = (this.firstCellZ + $$1) * this.cellWidth;
        this.arrayInterpolationCounter++;
        for (CacheAllInCell $$3 : this.cellCaches) {
            $$3.noiseFiller.fillArray($$3.values, this);
        }
        this.arrayInterpolationCounter++;
        this.fillingCell = false;
    }

    public void updateForY(int $$0, double $$1) {
        this.inCellY = $$0 - this.cellStartBlockY;
        for (NoiseInterpolator $$2 : this.interpolators) {
            $$2.updateForY($$1);
        }
    }

    public void updateForX(int $$0, double $$1) {
        this.inCellX = $$0 - this.cellStartBlockX;
        for (NoiseInterpolator $$2 : this.interpolators) {
            $$2.updateForX($$1);
        }
    }

    public void updateForZ(int $$0, double $$1) {
        this.inCellZ = $$0 - this.cellStartBlockZ;
        this.interpolationCounter++;
        for (NoiseInterpolator $$2 : this.interpolators) {
            $$2.updateForZ($$1);
        }
    }

    public void stopInterpolation() {
        if (!this.interpolating) {
            throw new IllegalStateException("Staring interpolation twice");
        }
        this.interpolating = false;
    }

    public void swapSlices() {
        this.interpolators.forEach((v0) -> {
            v0.swapSlices();
        });
    }

    public Aquifer aquifer() {
        return this.aquifer;
    }

    protected int cellWidth() {
        return this.cellWidth;
    }

    protected int cellHeight() {
        return this.cellHeight;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$NoiseChunkDensityFunction.class */
    interface NoiseChunkDensityFunction extends DensityFunction {
        DensityFunction wrapped();

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        default double minValue() {
            return wrapped().minValue();
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        default double maxValue() {
            return wrapped().maxValue();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$FlatCache.class */
    class FlatCache implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
        private final DensityFunction noiseFiller;
        final double[] values;
        final int sizeXZ;

        FlatCache(DensityFunction $$0, boolean $$1) {
            this.noiseFiller = $$0;
            this.sizeXZ = NoiseChunk.this.noiseSizeXZ + 1;
            this.values = new double[this.sizeXZ * this.sizeXZ];
            if ($$1) {
                for (int $$2 = 0; $$2 <= NoiseChunk.this.noiseSizeXZ; $$2++) {
                    int $$3 = NoiseChunk.this.firstNoiseX + $$2;
                    int $$4 = QuartPos.toBlock($$3);
                    for (int $$5 = 0; $$5 <= NoiseChunk.this.noiseSizeXZ; $$5++) {
                        int $$6 = NoiseChunk.this.firstNoiseZ + $$5;
                        int $$7 = QuartPos.toBlock($$6);
                        this.values[$$2 + ($$5 * this.sizeXZ)] = $$0.compute(new DensityFunction.SinglePointContext($$4, 0, $$7));
                    }
                }
            }
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            int $$1 = QuartPos.fromBlock($$0.blockX());
            int $$2 = QuartPos.fromBlock($$0.blockZ());
            int $$3 = $$1 - NoiseChunk.this.firstNoiseX;
            int $$4 = $$2 - NoiseChunk.this.firstNoiseZ;
            if ($$3 >= 0 && $$4 >= 0 && $$3 < this.sizeXZ && $$4 < this.sizeXZ) {
                return this.values[$$3 + ($$4 * this.sizeXZ)];
            }
            return this.noiseFiller.compute($$0);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            $$1.fillAllDirectly($$0, this);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunction wrapped() {
            return this.noiseFiller;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunctions.Marker.Type type() {
            return DensityFunctions.Marker.Type.FlatCache;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$CacheAllInCell.class */
    class CacheAllInCell implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
        final DensityFunction noiseFiller;
        final double[] values;

        CacheAllInCell(DensityFunction $$0) {
            this.noiseFiller = $$0;
            this.values = new double[NoiseChunk.this.cellWidth * NoiseChunk.this.cellWidth * NoiseChunk.this.cellHeight];
            NoiseChunk.this.cellCaches.add(this);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            if ($$0 != NoiseChunk.this) {
                return this.noiseFiller.compute($$0);
            }
            if (!NoiseChunk.this.interpolating) {
                throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
            }
            int $$1 = NoiseChunk.this.inCellX;
            int $$2 = NoiseChunk.this.inCellY;
            int $$3 = NoiseChunk.this.inCellZ;
            if ($$1 >= 0 && $$2 >= 0 && $$3 >= 0 && $$1 < NoiseChunk.this.cellWidth && $$2 < NoiseChunk.this.cellHeight && $$3 < NoiseChunk.this.cellWidth) {
                return this.values[(((((NoiseChunk.this.cellHeight - 1) - $$2) * NoiseChunk.this.cellWidth) + $$1) * NoiseChunk.this.cellWidth) + $$3];
            }
            return this.noiseFiller.compute($$0);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            $$1.fillAllDirectly($$0, this);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunction wrapped() {
            return this.noiseFiller;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunctions.Marker.Type type() {
            return DensityFunctions.Marker.Type.CacheAllInCell;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$NoiseInterpolator.class */
    public class NoiseInterpolator implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
        double[][] slice0;
        double[][] slice1;
        private final DensityFunction noiseFiller;
        private double noise000;
        private double noise001;
        private double noise100;
        private double noise101;
        private double noise010;
        private double noise011;
        private double noise110;
        private double noise111;
        private double valueXZ00;
        private double valueXZ10;
        private double valueXZ01;
        private double valueXZ11;
        private double valueZ0;
        private double valueZ1;
        private double value;

        NoiseInterpolator(DensityFunction $$1) {
            this.noiseFiller = $$1;
            this.slice0 = allocateSlice(NoiseChunk.this.cellCountY, NoiseChunk.this.cellCountXZ);
            this.slice1 = allocateSlice(NoiseChunk.this.cellCountY, NoiseChunk.this.cellCountXZ);
            NoiseChunk.this.interpolators.add(this);
        }

        private double[][] allocateSlice(int $$0, int $$1) {
            int $$2 = $$1 + 1;
            int $$3 = $$0 + 1;
            double[][] $$4 = new double[$$2][$$3];
            for (int $$5 = 0; $$5 < $$2; $$5++) {
                $$4[$$5] = new double[$$3];
            }
            return $$4;
        }

        void selectCellYZ(int $$0, int $$1) {
            this.noise000 = this.slice0[$$1][$$0];
            this.noise001 = this.slice0[$$1 + 1][$$0];
            this.noise100 = this.slice1[$$1][$$0];
            this.noise101 = this.slice1[$$1 + 1][$$0];
            this.noise010 = this.slice0[$$1][$$0 + 1];
            this.noise011 = this.slice0[$$1 + 1][$$0 + 1];
            this.noise110 = this.slice1[$$1][$$0 + 1];
            this.noise111 = this.slice1[$$1 + 1][$$0 + 1];
        }

        void updateForY(double $$0) {
            this.valueXZ00 = Mth.lerp($$0, this.noise000, this.noise010);
            this.valueXZ10 = Mth.lerp($$0, this.noise100, this.noise110);
            this.valueXZ01 = Mth.lerp($$0, this.noise001, this.noise011);
            this.valueXZ11 = Mth.lerp($$0, this.noise101, this.noise111);
        }

        void updateForX(double $$0) {
            this.valueZ0 = Mth.lerp($$0, this.valueXZ00, this.valueXZ10);
            this.valueZ1 = Mth.lerp($$0, this.valueXZ01, this.valueXZ11);
        }

        void updateForZ(double $$0) {
            this.value = Mth.lerp($$0, this.valueZ0, this.valueZ1);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            if ($$0 != NoiseChunk.this) {
                return this.noiseFiller.compute($$0);
            }
            if (!NoiseChunk.this.interpolating) {
                throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
            }
            if (NoiseChunk.this.fillingCell) {
                return Mth.lerp3(((double) NoiseChunk.this.inCellX) / ((double) NoiseChunk.this.cellWidth), ((double) NoiseChunk.this.inCellY) / ((double) NoiseChunk.this.cellHeight), ((double) NoiseChunk.this.inCellZ) / ((double) NoiseChunk.this.cellWidth), this.noise000, this.noise100, this.noise010, this.noise110, this.noise001, this.noise101, this.noise011, this.noise111);
            }
            return this.value;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            if (NoiseChunk.this.fillingCell) {
                $$1.fillAllDirectly($$0, this);
            } else {
                wrapped().fillArray($$0, $$1);
            }
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunction wrapped() {
            return this.noiseFiller;
        }

        private void swapSlices() {
            double[][] $$0 = this.slice0;
            this.slice0 = this.slice1;
            this.slice1 = $$0;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunctions.Marker.Type type() {
            return DensityFunctions.Marker.Type.Interpolated;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$CacheOnce.class */
    class CacheOnce implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
        private final DensityFunction function;
        private long lastCounter;
        private long lastArrayCounter;
        private double lastValue;
        private double[] lastArray;

        CacheOnce(DensityFunction $$0) {
            this.function = $$0;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            if ($$0 != NoiseChunk.this) {
                return this.function.compute($$0);
            }
            if (this.lastArray != null && this.lastArrayCounter == NoiseChunk.this.arrayInterpolationCounter) {
                return this.lastArray[NoiseChunk.this.arrayIndex];
            }
            if (this.lastCounter == NoiseChunk.this.interpolationCounter) {
                return this.lastValue;
            }
            this.lastCounter = NoiseChunk.this.interpolationCounter;
            double $$1 = this.function.compute($$0);
            this.lastValue = $$1;
            return $$1;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            if (this.lastArray != null && this.lastArrayCounter == NoiseChunk.this.arrayInterpolationCounter) {
                System.arraycopy(this.lastArray, 0, $$0, 0, $$0.length);
                return;
            }
            wrapped().fillArray($$0, $$1);
            if (this.lastArray != null && this.lastArray.length == $$0.length) {
                System.arraycopy($$0, 0, this.lastArray, 0, $$0.length);
            } else {
                this.lastArray = (double[]) $$0.clone();
            }
            this.lastArrayCounter = NoiseChunk.this.arrayInterpolationCounter;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunction wrapped() {
            return this.function;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunctions.Marker.Type type() {
            return DensityFunctions.Marker.Type.CacheOnce;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$Cache2D.class */
    static class Cache2D implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
        private final DensityFunction function;
        private long lastPos2D = ChunkPos.INVALID_CHUNK_POS;
        private double lastValue;

        Cache2D(DensityFunction $$0) {
            this.function = $$0;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            int $$1 = $$0.blockX();
            int $$2 = $$0.blockZ();
            long $$3 = ChunkPos.asLong($$1, $$2);
            if (this.lastPos2D == $$3) {
                return this.lastValue;
            }
            this.lastPos2D = $$3;
            double $$4 = this.function.compute($$0);
            this.lastValue = $$4;
            return $$4;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            this.function.fillArray($$0, $$1);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunction wrapped() {
            return this.function;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunctions.MarkerOrMarked
        public DensityFunctions.Marker.Type type() {
            return DensityFunctions.Marker.Type.Cache2D;
        }
    }

    Blender.BlendingOutput getOrComputeBlendingOutput(int $$0, int $$1) {
        long $$2 = ChunkPos.asLong($$0, $$1);
        if (this.lastBlendingDataPos == $$2) {
            return this.lastBlendingOutput;
        }
        this.lastBlendingDataPos = $$2;
        Blender.BlendingOutput $$3 = this.blender.blendOffsetAndFactor($$0, $$1);
        this.lastBlendingOutput = $$3;
        return $$3;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$BlendAlpha.class */
    class BlendAlpha implements NoiseChunkDensityFunction {
        BlendAlpha() {
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction
        public DensityFunction wrapped() {
            return DensityFunctions.BlendAlpha.INSTANCE;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public DensityFunction mapAll(DensityFunction.Visitor $$0) {
            return wrapped().mapAll($$0);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            return NoiseChunk.this.getOrComputeBlendingOutput($$0.blockX(), $$0.blockZ()).alpha();
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            $$1.fillAllDirectly($$0, this);
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction, net.minecraft.world.level.levelgen.DensityFunction
        public double minValue() {
            return Density.SURFACE;
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction, net.minecraft.world.level.levelgen.DensityFunction
        public double maxValue() {
            return 1.0d;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return DensityFunctions.BlendAlpha.CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/NoiseChunk$BlendOffset.class */
    class BlendOffset implements NoiseChunkDensityFunction {
        BlendOffset() {
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction
        public DensityFunction wrapped() {
            return DensityFunctions.BlendOffset.INSTANCE;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public DensityFunction mapAll(DensityFunction.Visitor $$0) {
            return wrapped().mapAll($$0);
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public double compute(DensityFunction.FunctionContext $$0) {
            return NoiseChunk.this.getOrComputeBlendingOutput($$0.blockX(), $$0.blockZ()).blendingOffset();
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
            $$1.fillAllDirectly($$0, this);
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction, net.minecraft.world.level.levelgen.DensityFunction
        public double minValue() {
            return Double.NEGATIVE_INFINITY;
        }

        @Override // net.minecraft.world.level.levelgen.NoiseChunk.NoiseChunkDensityFunction, net.minecraft.world.level.levelgen.DensityFunction
        public double maxValue() {
            return Double.POSITIVE_INFINITY;
        }

        @Override // net.minecraft.world.level.levelgen.DensityFunction
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return DensityFunctions.BlendOffset.CODEC;
        }
    }

    protected DensityFunction wrap(DensityFunction $$0) {
        return this.wrapped.computeIfAbsent($$0, this::wrapNew);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private DensityFunction wrapNew(DensityFunction $$0) throws MatchException {
        if ($$0 instanceof DensityFunctions.Marker) {
            DensityFunctions.Marker $$1 = (DensityFunctions.Marker) $$0;
            switch ($$1.type()) {
                case Interpolated:
                    return new NoiseInterpolator($$1.wrapped());
                case FlatCache:
                    return new FlatCache($$1.wrapped(), true);
                case Cache2D:
                    return new Cache2D($$1.wrapped());
                case CacheOnce:
                    return new CacheOnce($$1.wrapped());
                case CacheAllInCell:
                    return new CacheAllInCell($$1.wrapped());
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        if (this.blender != Blender.empty()) {
            if ($$0 == DensityFunctions.BlendAlpha.INSTANCE) {
                return this.blendAlpha;
            }
            if ($$0 == DensityFunctions.BlendOffset.INSTANCE) {
                return this.blendOffset;
            }
        }
        if ($$0 == DensityFunctions.BeardifierMarker.INSTANCE) {
            return this.beardifier;
        }
        if ($$0 instanceof DensityFunctions.HolderHolder) {
            DensityFunctions.HolderHolder $$2 = (DensityFunctions.HolderHolder) $$0;
            return $$2.function().value();
        }
        return $$0;
    }
}
