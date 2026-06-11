package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Scoreboard;
import org.apache.commons.lang3.mutable.MutableBoolean;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/Level.class */
public abstract class Level implements LevelAccessor, AutoCloseable {
    public static final int MAX_LEVEL_SIZE = 30000000;
    public static final int LONG_PARTICLE_CLIP_RANGE = 512;
    public static final int SHORT_PARTICLE_CLIP_RANGE = 32;
    public static final int MAX_BRIGHTNESS = 15;
    public static final int MAX_ENTITY_SPAWN_Y = 20000000;
    public static final int MIN_ENTITY_SPAWN_Y = -20000000;
    protected final CollectingNeighborUpdater neighborUpdater;
    private boolean tickingBlockEntities;
    private final boolean isDebug;
    private int skyDarken;
    protected float oRainLevel;
    protected float rainLevel;
    protected float oThunderLevel;
    protected float thunderLevel;
    private final Holder<DimensionType> dimensionTypeRegistration;
    protected final WritableLevelData levelData;
    private final boolean isClientSide;
    private final BiomeManager biomeManager;
    private final ResourceKey<Level> dimension;
    private final RegistryAccess registryAccess;
    private final DamageSources damageSources;
    private final PalettedContainerFactory palettedContainerFactory;
    private long subTickCount;
    public static final Codec<ResourceKey<Level>> RESOURCE_KEY_CODEC = ResourceKey.codec(Registries.DIMENSION);
    public static final ResourceKey<Level> OVERWORLD = ResourceKey.create(Registries.DIMENSION, Identifier.withDefaultNamespace("overworld"));
    public static final ResourceKey<Level> NETHER = ResourceKey.create(Registries.DIMENSION, Identifier.withDefaultNamespace("the_nether"));
    public static final ResourceKey<Level> END = ResourceKey.create(Registries.DIMENSION, Identifier.withDefaultNamespace("the_end"));
    private static final WeightedList<ExplosionParticleInfo> DEFAULT_EXPLOSION_BLOCK_PARTICLES = WeightedList.builder().add(new ExplosionParticleInfo(ParticleTypes.POOF, 0.5f, 1.0f)).add(new ExplosionParticleInfo(ParticleTypes.SMOKE, 1.0f, 1.0f)).build();
    protected final List<TickingBlockEntity> blockEntityTickers = Lists.newArrayList();
    private final List<TickingBlockEntity> pendingBlockEntityTickers = Lists.newArrayList();
    protected int randValue = RandomSource.create().nextInt();
    protected final int addend = 1013904223;
    public final RandomSource random = RandomSource.create();

    @Deprecated
    private final RandomSource threadSafeRandom = RandomSource.createThreadSafe();
    private final Thread thread = Thread.currentThread();

    public abstract void sendBlockUpdated(BlockPos blockPos, BlockState blockState, BlockState blockState2, @Block.UpdateFlags int i);

    public abstract void playSeededSound(Entity entity, double d, double d2, double d3, Holder<SoundEvent> holder, SoundSource soundSource, float f, float f2, long j);

    public abstract void playSeededSound(Entity entity, Entity entity2, Holder<SoundEvent> holder, SoundSource soundSource, float f, float f2, long j);

    public abstract void explode(Entity entity, DamageSource damageSource, ExplosionDamageCalculator explosionDamageCalculator, double d, double d2, double d3, float f, boolean z, ExplosionInteraction explosionInteraction, ParticleOptions particleOptions, ParticleOptions particleOptions2, WeightedList<ExplosionParticleInfo> weightedList, Holder<SoundEvent> holder);

    public abstract String gatherChunkSourceStats();

    public abstract void setRespawnData(LevelData.RespawnData respawnData);

    public abstract LevelData.RespawnData getRespawnData();

    public abstract Entity getEntity(int i);

    public abstract Collection<EnderDragonPart> dragonParts();

    public abstract TickRateManager tickRateManager();

    public abstract MapItemSavedData getMapData(MapId mapId);

    public abstract void destroyBlockProgress(int i, BlockPos blockPos, int i2);

    public abstract Scoreboard getScoreboard();

    public abstract RecipeAccess recipeAccess();

    protected abstract LevelEntityGetter<Entity> getEntities();

    public abstract EnvironmentAttributeSystem environmentAttributes();

    public abstract PotionBrewing potionBrewing();

    public abstract FuelValues fuelValues();

    protected Level(WritableLevelData $$0, ResourceKey<Level> $$1, RegistryAccess $$2, Holder<DimensionType> $$3, boolean $$4, boolean $$5, long $$6, int $$7) {
        this.levelData = $$0;
        this.dimensionTypeRegistration = $$3;
        this.dimension = $$1;
        this.isClientSide = $$4;
        this.biomeManager = new BiomeManager(this, $$6);
        this.isDebug = $$5;
        this.neighborUpdater = new CollectingNeighborUpdater(this, $$7);
        this.registryAccess = $$2;
        this.palettedContainerFactory = PalettedContainerFactory.create($$2);
        this.damageSources = new DamageSources($$2);
    }

    @Override // net.minecraft.world.level.LevelReader
    public boolean isClientSide() {
        return this.isClientSide;
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public MinecraftServer getServer() {
        return null;
    }

    public boolean isInWorldBounds(BlockPos $$0) {
        return !isOutsideBuildHeight($$0) && isInWorldBoundsHorizontal($$0);
    }

    public boolean isInValidBounds(BlockPos $$0) {
        return !isOutsideBuildHeight($$0) && isInValidBoundsHorizontal($$0);
    }

    public static boolean isInSpawnableBounds(BlockPos $$0) {
        return !isOutsideSpawnableHeight($$0.getY()) && isInWorldBoundsHorizontal($$0);
    }

    private static boolean isInWorldBoundsHorizontal(BlockPos $$0) {
        return $$0.getX() >= -30000000 && $$0.getZ() >= -30000000 && $$0.getX() < 30000000 && $$0.getZ() < 30000000;
    }

    private static boolean isInValidBoundsHorizontal(BlockPos $$0) {
        int $$1 = SectionPos.blockToSectionCoord($$0.getX());
        int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
        return ChunkPos.isValid($$1, $$2);
    }

    private static boolean isOutsideSpawnableHeight(int $$0) {
        return $$0 < -20000000 || $$0 >= 20000000;
    }

    public LevelChunk getChunkAt(BlockPos $$0) {
        return getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
    }

    @Override // net.minecraft.world.level.LevelReader
    public LevelChunk getChunk(int $$0, int $$1) {
        return (LevelChunk) getChunk($$0, $$1, ChunkStatus.FULL);
    }

    @Override // net.minecraft.world.level.LevelReader
    public ChunkAccess getChunk(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
        ChunkAccess $$4 = getChunkSource().getChunk($$0, $$1, $$2, $$3);
        if ($$4 == null && $$3) {
            throw new IllegalStateException("Should always be able to create a chunk!");
        }
        return $$4;
    }

    @Override // net.minecraft.world.level.LevelWriter
    public boolean setBlock(BlockPos $$0, BlockState $$1, @Block.UpdateFlags int $$2) {
        return setBlock($$0, $$1, $$2, 512);
    }

    public boolean setBlock(BlockPos $$0, BlockState $$1, @Block.UpdateFlags int $$2, int $$3) {
        if (!isInValidBounds($$0)) {
            return false;
        }
        if (!isClientSide() && isDebug()) {
            return false;
        }
        LevelChunk $$4 = getChunkAt($$0);
        Block $$5 = $$1.getBlock();
        BlockState $$6 = $$4.setBlockState($$0, $$1, $$2);
        if ($$6 != null) {
            BlockState $$7 = getBlockState($$0);
            if ($$7 == $$1) {
                if ($$6 != $$7) {
                    setBlocksDirty($$0, $$6, $$7);
                }
                if (($$2 & 2) != 0 && ((!isClientSide() || ($$2 & 4) == 0) && (isClientSide() || ($$4.getFullStatus() != null && $$4.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING))))) {
                    sendBlockUpdated($$0, $$6, $$1, $$2);
                }
                if (($$2 & 1) != 0) {
                    updateNeighborsAt($$0, $$6.getBlock());
                    if (!isClientSide() && $$1.hasAnalogOutputSignal()) {
                        updateNeighbourForOutputSignal($$0, $$5);
                    }
                }
                if (($$2 & 16) == 0 && $$3 > 0) {
                    int $$8 = $$2 & (-34);
                    $$6.updateIndirectNeighbourShapes(this, $$0, $$8, $$3 - 1);
                    $$1.updateNeighbourShapes(this, $$0, $$8, $$3 - 1);
                    $$1.updateIndirectNeighbourShapes(this, $$0, $$8, $$3 - 1);
                }
                updatePOIOnBlockStateChange($$0, $$6, $$7);
                return true;
            }
            return true;
        }
        return false;
    }

    public void updatePOIOnBlockStateChange(BlockPos $$0, BlockState $$1, BlockState $$2) {
    }

    @Override // net.minecraft.world.level.LevelWriter
    public boolean removeBlock(BlockPos $$0, boolean $$1) {
        FluidState $$2 = getFluidState($$0);
        return setBlock($$0, $$2.createLegacyBlock(), 3 | ($$1 ? 64 : 0));
    }

    @Override // net.minecraft.world.level.LevelWriter
    public boolean destroyBlock(BlockPos $$0, boolean $$1, Entity $$2, int $$3) {
        BlockState $$4 = getBlockState($$0);
        if ($$4.isAir()) {
            return false;
        }
        FluidState $$5 = getFluidState($$0);
        if (!($$4.getBlock() instanceof BaseFireBlock)) {
            levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, $$0, Block.getId($$4));
        }
        if ($$1) {
            BlockEntity $$6 = $$4.hasBlockEntity() ? getBlockEntity($$0) : null;
            Block.dropResources($$4, this, $$0, $$6, $$2, ItemStack.EMPTY);
        }
        boolean $$7 = setBlock($$0, $$5.createLegacyBlock(), 3, $$3);
        if ($$7) {
            gameEvent(GameEvent.BLOCK_DESTROY, $$0, GameEvent.Context.of($$2, $$4));
        }
        return $$7;
    }

    public void addDestroyBlockEffect(BlockPos $$0, BlockState $$1) {
    }

    public boolean setBlockAndUpdate(BlockPos $$0, BlockState $$1) {
        return setBlock($$0, $$1, 3);
    }

    public void setBlocksDirty(BlockPos $$0, BlockState $$1, BlockState $$2) {
    }

    public void updateNeighborsAt(BlockPos $$0, Block $$1, Orientation $$2) {
    }

    public void updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, Direction $$2, Orientation $$3) {
    }

    public void neighborChanged(BlockPos $$0, Block $$1, Orientation $$2) {
    }

    public void neighborChanged(BlockState $$0, BlockPos $$1, Block $$2, Orientation $$3, boolean $$4) {
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public void neighborShapeChanged(Direction $$0, BlockPos $$1, BlockPos $$2, BlockState $$3, @Block.UpdateFlags int $$4, int $$5) {
        this.neighborUpdater.shapeUpdate($$0, $$3, $$1, $$2, $$4, $$5);
    }

    @Override // net.minecraft.world.level.LevelReader
    public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
        int $$5;
        if ($$1 < -30000000 || $$2 < -30000000 || $$1 >= 30000000 || $$2 >= 30000000) {
            $$5 = getSeaLevel() + 1;
        } else if (hasChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2))) {
            $$5 = getChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2)).getHeight($$0, $$1 & 15, $$2 & 15) + 1;
        } else {
            $$5 = getMinY();
        }
        return $$5;
    }

    @Override // net.minecraft.world.level.BlockAndTintGetter
    public LevelLightEngine getLightEngine() {
        return getChunkSource().getLightEngine();
    }

    @Override // net.minecraft.world.level.BlockGetter
    public BlockState getBlockState(BlockPos $$0) {
        if (!isInValidBounds($$0)) {
            return Blocks.VOID_AIR.defaultBlockState();
        }
        LevelChunk $$1 = getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
        return $$1.getBlockState($$0);
    }

    @Override // net.minecraft.world.level.BlockGetter
    public FluidState getFluidState(BlockPos $$0) {
        if (!isInValidBounds($$0)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        LevelChunk $$1 = getChunkAt($$0);
        return $$1.getFluidState($$0);
    }

    public boolean isBrightOutside() {
        return !dimensionType().hasFixedTime() && this.skyDarken < 4;
    }

    public boolean isDarkOutside() {
        return (dimensionType().hasFixedTime() || isBrightOutside()) ? false : true;
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public void playSound(Entity $$0, BlockPos $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {
        playSound($$0, ((double) $$1.getX()) + 0.5d, ((double) $$1.getY()) + 0.5d, ((double) $$1.getZ()) + 0.5d, $$2, $$3, $$4, $$5);
    }

    public void playSeededSound(Entity $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5, float $$6, float $$7, long $$8) {
        playSeededSound($$0, $$1, $$2, $$3, BuiltInRegistries.SOUND_EVENT.wrapAsHolder($$4), $$5, $$6, $$7, $$8);
    }

    public void playSound(Entity $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5) {
        playSound($$0, $$1, $$2, $$3, $$4, $$5, 1.0f, 1.0f);
    }

    public void playSound(Entity $$0, double $$1, double $$2, double $$3, SoundEvent $$4, SoundSource $$5, float $$6, float $$7) {
        playSeededSound($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.threadSafeRandom.nextLong());
    }

    public void playSound(Entity $$0, double $$1, double $$2, double $$3, Holder<SoundEvent> $$4, SoundSource $$5, float $$6, float $$7) {
        playSeededSound($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.threadSafeRandom.nextLong());
    }

    public void playSound(Entity $$0, Entity $$1, SoundEvent $$2, SoundSource $$3, float $$4, float $$5) {
        playSeededSound($$0, $$1, BuiltInRegistries.SOUND_EVENT.wrapAsHolder($$2), $$3, $$4, $$5, this.threadSafeRandom.nextLong());
    }

    public void playLocalSound(BlockPos $$0, SoundEvent $$1, SoundSource $$2, float $$3, float $$4, boolean $$5) {
        playLocalSound(((double) $$0.getX()) + 0.5d, ((double) $$0.getY()) + 0.5d, ((double) $$0.getZ()) + 0.5d, $$1, $$2, $$3, $$4, $$5);
    }

    public void playLocalSound(Entity $$0, SoundEvent $$1, SoundSource $$2, float $$3, float $$4) {
    }

    public void playLocalSound(double $$0, double $$1, double $$2, SoundEvent $$3, SoundSource $$4, float $$5, float $$6, boolean $$7) {
    }

    public void playPlayerSound(SoundEvent $$0, SoundSource $$1, float $$2, float $$3) {
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public void addParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
    }

    public void addParticle(ParticleOptions $$0, boolean $$1, boolean $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8) {
    }

    public void addAlwaysVisibleParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
    }

    public void addAlwaysVisibleParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
    }

    public void addBlockEntityTicker(TickingBlockEntity $$0) {
        (this.tickingBlockEntities ? this.pendingBlockEntityTickers : this.blockEntityTickers).add($$0);
    }

    public void tickBlockEntities() {
        this.tickingBlockEntities = true;
        if (!this.pendingBlockEntityTickers.isEmpty()) {
            this.blockEntityTickers.addAll(this.pendingBlockEntityTickers);
            this.pendingBlockEntityTickers.clear();
        }
        Iterator<TickingBlockEntity> $$0 = this.blockEntityTickers.iterator();
        boolean $$1 = tickRateManager().runsNormally();
        while ($$0.hasNext()) {
            TickingBlockEntity $$2 = $$0.next();
            if ($$2.isRemoved()) {
                $$0.remove();
            } else if ($$1 && shouldTickBlocksAt($$2.getPos())) {
                $$2.tick();
            }
        }
        this.tickingBlockEntities = false;
    }

    public <T extends Entity> void guardEntityTick(Consumer<T> $$0, T $$1) {
        try {
            $$0.accept($$1);
        } catch (Throwable $$2) {
            CrashReport $$3 = CrashReport.forThrowable($$2, "Ticking entity");
            CrashReportCategory $$4 = $$3.addCategory("Entity being ticked");
            $$1.fillCrashReportCategory($$4);
            throw new ReportedException($$3);
        }
    }

    public boolean shouldTickDeath(Entity $$0) {
        return true;
    }

    public boolean shouldTickBlocksAt(long $$0) {
        return true;
    }

    public boolean shouldTickBlocksAt(BlockPos $$0) {
        return shouldTickBlocksAt(ChunkPos.asLong($$0));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/Level$ExplosionInteraction.class */
    public enum ExplosionInteraction implements StringRepresentable {
        NONE("none"),
        BLOCK("block"),
        MOB("mob"),
        TNT("tnt"),
        TRIGGER("trigger");

        public static final Codec<ExplosionInteraction> CODEC = StringRepresentable.fromEnum(ExplosionInteraction::values);
        private final String id;

        ExplosionInteraction(String $$0) {
            this.id = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.id;
        }
    }

    public void explode(Entity $$0, double $$1, double $$2, double $$3, float $$4, ExplosionInteraction $$5) {
        explode($$0, Explosion.getDefaultDamageSource(this, $$0), null, $$1, $$2, $$3, $$4, false, $$5, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, DEFAULT_EXPLOSION_BLOCK_PARTICLES, SoundEvents.GENERIC_EXPLODE);
    }

    public void explode(Entity $$0, double $$1, double $$2, double $$3, float $$4, boolean $$5, ExplosionInteraction $$6) {
        explode($$0, Explosion.getDefaultDamageSource(this, $$0), null, $$1, $$2, $$3, $$4, $$5, $$6, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, DEFAULT_EXPLOSION_BLOCK_PARTICLES, SoundEvents.GENERIC_EXPLODE);
    }

    public void explode(Entity $$0, DamageSource $$1, ExplosionDamageCalculator $$2, Vec3 $$3, float $$4, boolean $$5, ExplosionInteraction $$6) {
        explode($$0, $$1, $$2, $$3.x(), $$3.y(), $$3.z(), $$4, $$5, $$6, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, DEFAULT_EXPLOSION_BLOCK_PARTICLES, SoundEvents.GENERIC_EXPLODE);
    }

    public void explode(Entity $$0, DamageSource $$1, ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, ExplosionInteraction $$8) {
        explode($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, DEFAULT_EXPLOSION_BLOCK_PARTICLES, SoundEvents.GENERIC_EXPLODE);
    }

    @Override // net.minecraft.world.level.BlockGetter
    public BlockEntity getBlockEntity(BlockPos $$0) {
        if (!isInValidBounds($$0)) {
            return null;
        }
        if (!isClientSide() && Thread.currentThread() != this.thread) {
            return null;
        }
        return getChunkAt($$0).getBlockEntity($$0, LevelChunk.EntityCreationType.IMMEDIATE);
    }

    public void setBlockEntity(BlockEntity $$0) {
        BlockPos $$1 = $$0.getBlockPos();
        if (!isInValidBounds($$1)) {
            return;
        }
        getChunkAt($$1).addAndRegisterBlockEntity($$0);
    }

    public void removeBlockEntity(BlockPos $$0) {
        if (!isInValidBounds($$0)) {
            return;
        }
        getChunkAt($$0).removeBlockEntity($$0);
    }

    public boolean isLoaded(BlockPos $$0) {
        if (!isInValidBounds($$0)) {
            return false;
        }
        return getChunkSource().hasChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
    }

    public boolean loadedAndEntityCanStandOnFace(BlockPos $$0, Entity $$1, Direction $$2) {
        ChunkAccess $$3;
        if (!isInValidBounds($$0) || ($$3 = getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()), ChunkStatus.FULL, false)) == null) {
            return false;
        }
        return $$3.getBlockState($$0).entityCanStandOnFace(this, $$0, $$1, $$2);
    }

    public boolean loadedAndEntityCanStandOn(BlockPos $$0, Entity $$1) {
        return loadedAndEntityCanStandOnFace($$0, $$1, Direction.UP);
    }

    public void updateSkyBrightness() {
        this.skyDarken = (int) (15.0f - ((Float) environmentAttributes().getDimensionValue(EnvironmentAttributes.SKY_LIGHT_LEVEL)).floatValue());
    }

    public void setSpawnSettings(boolean $$0) {
        getChunkSource().setSpawnSettings($$0);
    }

    public LevelData.RespawnData getWorldBorderAdjustedRespawnData(LevelData.RespawnData $$0) {
        WorldBorder $$1 = getWorldBorder();
        if (!$$1.isWithinBounds($$0.pos())) {
            BlockPos $$2 = getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, BlockPos.containing($$1.getCenterX(), Density.SURFACE, $$1.getCenterZ()));
            return LevelData.RespawnData.of($$0.dimension(), $$2, $$0.yaw(), $$0.pitch());
        }
        return $$0;
    }

    protected void prepareWeather() {
        if (this.levelData.isRaining()) {
            this.rainLevel = 1.0f;
            if (this.levelData.isThundering()) {
                this.thunderLevel = 1.0f;
            }
        }
    }

    public void close() throws IOException {
        getChunkSource().close();
    }

    @Override // net.minecraft.world.level.LevelReader, net.minecraft.world.level.CollisionGetter
    public BlockGetter getChunkForCollisions(int $$0, int $$1) {
        return getChunk($$0, $$1, ChunkStatus.FULL, false);
    }

    @Override // net.minecraft.world.level.EntityGetter
    public List<Entity> getEntities(Entity $$0, AABB $$1, Predicate<? super Entity> $$2) {
        Profiler.get().incrementCounter("getEntities");
        List<Entity> $$3 = Lists.newArrayList();
        getEntities().get($$1, $$32 -> {
            if ($$32 != $$0 && $$2.test($$32)) {
                $$3.add($$32);
            }
        });
        for (EnderDragonPart $$4 : dragonParts()) {
            if ($$4 != $$0 && $$4.parentMob != $$0 && $$2.test($$4) && $$1.intersects($$4.getBoundingBox())) {
                $$3.add($$4);
            }
        }
        return $$3;
    }

    @Override // net.minecraft.world.level.EntityGetter
    public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2) {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        getEntities($$0, $$1, $$2, arrayListNewArrayList);
        return arrayListNewArrayList;
    }

    public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2, List<? super T> $$3) {
        getEntities($$0, $$1, $$2, $$3, Integer.MAX_VALUE);
    }

    public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2, List<? super T> $$3, int $$4) {
        Profiler.get().incrementCounter("getEntities");
        getEntities().get($$0, $$1, $$42 -> {
            if ($$2.test($$42)) {
                $$3.add($$42);
                if ($$3.size() >= $$4) {
                    return AbortableIterationConsumer.Continuation.ABORT;
                }
            }
            if ($$42 instanceof EnderDragon) {
                EnderDragon $$5 = (EnderDragon) $$42;
                for (EnderDragonPart $$6 : $$5.getSubEntities()) {
                    Entity entity = (Entity) $$0.tryCast($$6);
                    if (entity != null && $$2.test(entity)) {
                        $$3.add(entity);
                        if ($$3.size() >= $$4) {
                            return AbortableIterationConsumer.Continuation.ABORT;
                        }
                    }
                }
            }
            return AbortableIterationConsumer.Continuation.CONTINUE;
        });
    }

    public <T extends Entity> boolean hasEntities(EntityTypeTest<Entity, T> $$0, AABB $$1, Predicate<? super T> $$2) {
        Profiler.get().incrementCounter("hasEntities");
        MutableBoolean $$3 = new MutableBoolean();
        getEntities().get($$0, $$1, $$32 -> {
            if ($$2.test($$32)) {
                $$3.setTrue();
                return AbortableIterationConsumer.Continuation.ABORT;
            }
            if ($$32 instanceof EnderDragon) {
                EnderDragon $$4 = (EnderDragon) $$32;
                for (EnderDragonPart $$5 : $$4.getSubEntities()) {
                    Entity entity = (Entity) $$0.tryCast($$5);
                    if (entity != null && $$2.test(entity)) {
                        $$3.setTrue();
                        return AbortableIterationConsumer.Continuation.ABORT;
                    }
                }
            }
            return AbortableIterationConsumer.Continuation.CONTINUE;
        });
        return $$3.isTrue();
    }

    public List<Entity> getPushableEntities(Entity $$0, AABB $$1) {
        return getEntities($$0, $$1, EntitySelector.pushableBy($$0));
    }

    public Entity getEntity(UUID $$0) {
        return (Entity) getEntities().get($$0);
    }

    public Entity getEntityInAnyDimension(UUID $$0) {
        return getEntity($$0);
    }

    public Player getPlayerInAnyDimension(UUID $$0) {
        return getPlayerByUUID($$0);
    }

    public void blockEntityChanged(BlockPos $$0) {
        if (hasChunkAt($$0)) {
            getChunkAt($$0).markUnsaved();
        }
    }

    public void onBlockEntityAdded(BlockEntity $$0) {
    }

    public long getDayTime() {
        return this.levelData.getDayTime();
    }

    public boolean mayInteract(Entity $$0, BlockPos $$1) {
        return true;
    }

    public void broadcastEntityEvent(Entity $$0, byte $$1) {
    }

    public void broadcastDamageEvent(Entity $$0, DamageSource $$1) {
    }

    public void blockEvent(BlockPos $$0, Block $$1, int $$2, int $$3) {
        getBlockState($$0).triggerEvent(this, $$0, $$2, $$3);
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public LevelData getLevelData() {
        return this.levelData;
    }

    public float getThunderLevel(float $$0) {
        return Mth.lerp($$0, this.oThunderLevel, this.thunderLevel) * getRainLevel($$0);
    }

    public void setThunderLevel(float $$0) {
        float $$1 = Mth.clamp($$0, 0.0f, 1.0f);
        this.oThunderLevel = $$1;
        this.thunderLevel = $$1;
    }

    public float getRainLevel(float $$0) {
        return Mth.lerp($$0, this.oRainLevel, this.rainLevel);
    }

    public void setRainLevel(float $$0) {
        float $$1 = Mth.clamp($$0, 0.0f, 1.0f);
        this.oRainLevel = $$1;
        this.rainLevel = $$1;
    }

    public boolean canHaveWeather() {
        return (!dimensionType().hasSkyLight() || dimensionType().hasCeiling() || dimension() == END) ? false : true;
    }

    public boolean isThundering() {
        return canHaveWeather() && ((double) getThunderLevel(1.0f)) > 0.9d;
    }

    public boolean isRaining() {
        return canHaveWeather() && ((double) getRainLevel(1.0f)) > 0.2d;
    }

    public boolean isRainingAt(BlockPos $$0) {
        return precipitationAt($$0) == Biome.Precipitation.RAIN;
    }

    public Biome.Precipitation precipitationAt(BlockPos $$0) {
        if (!isRaining()) {
            return Biome.Precipitation.NONE;
        }
        if (!canSeeSky($$0)) {
            return Biome.Precipitation.NONE;
        }
        if (getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$0).getY() > $$0.getY()) {
            return Biome.Precipitation.NONE;
        }
        Biome $$1 = getBiome($$0).value();
        return $$1.getPrecipitationAt($$0, getSeaLevel());
    }

    public void globalLevelEvent(int $$0, BlockPos $$1, int $$2) {
    }

    public CrashReportCategory fillReportDetails(CrashReport $$0) {
        CrashReportCategory $$1 = $$0.addCategory("Affected level", 1);
        $$1.setDetail("All players", () -> {
            List<? extends Player> $$02 = players();
            return $$02.size() + " total; " + ((String) $$02.stream().map((v0) -> {
                return v0.debugInfo();
            }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT)));
        });
        ChunkSource chunkSource = getChunkSource();
        Objects.requireNonNull(chunkSource);
        $$1.setDetail("Chunk stats", chunkSource::gatherStats);
        $$1.setDetail("Level dimension", () -> {
            return dimension().identifier().toString();
        });
        try {
            this.levelData.fillCrashReportCategory($$1, this);
        } catch (Throwable $$2) {
            $$1.setDetailError("Level Data Unobtainable", $$2);
        }
        return $$1;
    }

    public void createFireworks(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, List<FireworkExplosion> $$6) {
    }

    public void updateNeighbourForOutputSignal(BlockPos $$0, Block $$1) {
        for (Direction $$2 : Direction.Plane.HORIZONTAL) {
            BlockPos $$3 = $$0.relative($$2);
            if (hasChunkAt($$3)) {
                BlockState $$4 = getBlockState($$3);
                if ($$4.is(Blocks.COMPARATOR)) {
                    neighborChanged($$4, $$3, $$1, null, false);
                } else if ($$4.isRedstoneConductor(this, $$3)) {
                    BlockPos $$32 = $$3.relative($$2);
                    BlockState $$42 = getBlockState($$32);
                    if ($$42.is(Blocks.COMPARATOR)) {
                        neighborChanged($$42, $$32, $$1, null, false);
                    }
                }
            }
        }
    }

    @Override // net.minecraft.world.level.LevelReader
    public int getSkyDarken() {
        return this.skyDarken;
    }

    public void setSkyFlashTime(int $$0) {
    }

    public void sendPacketToServer(Packet<?> $$0) {
        throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
    }

    @Override // net.minecraft.world.level.LevelReader
    public DimensionType dimensionType() {
        return this.dimensionTypeRegistration.value();
    }

    public Holder<DimensionType> dimensionTypeRegistration() {
        return this.dimensionTypeRegistration;
    }

    public ResourceKey<Level> dimension() {
        return this.dimension;
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public RandomSource getRandom() {
        return this.random;
    }

    @Override // net.minecraft.world.level.LevelSimulatedReader
    public boolean isStateAtPosition(BlockPos $$0, Predicate<BlockState> $$1) {
        return $$1.test(getBlockState($$0));
    }

    @Override // net.minecraft.world.level.LevelSimulatedReader
    public boolean isFluidAtPosition(BlockPos $$0, Predicate<FluidState> $$1) {
        return $$1.test(getFluidState($$0));
    }

    public BlockPos getBlockRandomPos(int $$0, int $$1, int $$2, int $$3) {
        this.randValue = (this.randValue * 3) + 1013904223;
        int $$4 = this.randValue >> 2;
        return new BlockPos($$0 + ($$4 & 15), $$1 + (($$4 >> 16) & $$3), $$2 + (($$4 >> 8) & 15));
    }

    public boolean noSave() {
        return false;
    }

    @Override // net.minecraft.world.level.LevelReader
    public BiomeManager getBiomeManager() {
        return this.biomeManager;
    }

    public final boolean isDebug() {
        return this.isDebug;
    }

    @Override // net.minecraft.world.level.LevelAccessor
    public long nextSubTickCount() {
        long j = this.subTickCount;
        this.subTickCount = j + 1;
        return j;
    }

    @Override // net.minecraft.world.level.LevelReader
    public RegistryAccess registryAccess() {
        return this.registryAccess;
    }

    public DamageSources damageSources() {
        return this.damageSources;
    }

    public int getClientLeafTintColor(BlockPos $$0) {
        return 0;
    }

    public PalettedContainerFactory palettedContainerFactory() {
        return this.palettedContainerFactory;
    }
}
