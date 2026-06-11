package net.minecraft.world.level.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/Block.class */
public class Block extends BlockBehaviour implements ItemLike {
    private final Holder.Reference<Block> builtInRegistryHolder;
    public static final int UPDATE_NEIGHBORS = 1;
    public static final int UPDATE_CLIENTS = 2;
    public static final int UPDATE_INVISIBLE = 4;
    public static final int UPDATE_IMMEDIATE = 8;
    public static final int UPDATE_KNOWN_SHAPE = 16;
    public static final int UPDATE_SUPPRESS_DROPS = 32;
    public static final int UPDATE_MOVE_BY_PISTON = 64;
    public static final int UPDATE_SKIP_SHAPE_UPDATE_ON_WIRE = 128;
    public static final int UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS = 256;
    public static final int UPDATE_SKIP_ON_PLACE = 512;

    @UpdateFlags
    public static final int UPDATE_NONE = 260;

    @UpdateFlags
    public static final int UPDATE_ALL = 3;

    @UpdateFlags
    public static final int UPDATE_ALL_IMMEDIATE = 11;

    @UpdateFlags
    public static final int UPDATE_SKIP_ALL_SIDEEFFECTS = 816;
    public static final float INDESTRUCTIBLE = -1.0f;
    public static final float INSTANT = 0.0f;
    public static final int UPDATE_LIMIT = 512;
    protected final StateDefinition<Block, BlockState> stateDefinition;
    private BlockState defaultBlockState;
    private Item item;
    private static final int CACHE_SIZE = 256;
    public static final MapCodec<Block> CODEC = simpleCodec(Block::new);
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final IdMapper<BlockState> BLOCK_STATE_REGISTRY = new IdMapper<>();
    private static final LoadingCache<VoxelShape, Boolean> SHAPE_FULL_BLOCK_CACHE = CacheBuilder.newBuilder().maximumSize(512).weakKeys().build(new CacheLoader<VoxelShape, Boolean>() { // from class: net.minecraft.world.level.block.Block.1
        public Boolean load(VoxelShape $$0) {
            return Boolean.valueOf(!Shapes.joinIsNotEmpty(Shapes.block(), $$0, BooleanOp.NOT_SAME));
        }
    });
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<ShapePairKey>> OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
        Object2ByteLinkedOpenHashMap<ShapePairKey> $$0 = new Object2ByteLinkedOpenHashMap<ShapePairKey>(256, 0.25f) { // from class: net.minecraft.world.level.block.Block.2
            protected void rehash(int $$02) {
            }
        };
        $$0.defaultReturnValue((byte) 127);
        return $$0;
    });

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/Block$UpdateFlags.class */
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    public @interface UpdateFlags {
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public static int getId(BlockState $$0) {
        int $$1;
        if ($$0 == null || ($$1 = BLOCK_STATE_REGISTRY.getId($$0)) == -1) {
            return 0;
        }
        return $$1;
    }

    public static BlockState stateById(int $$0) {
        BlockState $$1 = BLOCK_STATE_REGISTRY.byId($$0);
        return $$1 == null ? Blocks.AIR.defaultBlockState() : $$1;
    }

    public static Block byItem(Item $$0) {
        if ($$0 instanceof BlockItem) {
            return ((BlockItem) $$0).getBlock();
        }
        return Blocks.AIR;
    }

    public static BlockState pushEntitiesUp(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3) {
        VoxelShape $$4 = Shapes.joinUnoptimized($$0.getCollisionShape($$2, $$3), $$1.getCollisionShape($$2, $$3), BooleanOp.ONLY_SECOND).move($$3);
        if ($$4.isEmpty()) {
            return $$1;
        }
        List<Entity> $$5 = $$2.getEntities(null, $$4.bounds());
        for (Entity $$6 : $$5) {
            double $$7 = Shapes.collide(Direction.Axis.Y, $$6.getBoundingBox().move(Density.SURFACE, 1.0d, Density.SURFACE), List.of($$4), -1.0d);
            $$6.teleportRelative(Density.SURFACE, 1.0d + $$7, Density.SURFACE);
        }
        return $$1;
    }

    public static VoxelShape box(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
        return Shapes.box($$0 / 16.0d, $$1 / 16.0d, $$2 / 16.0d, $$3 / 16.0d, $$4 / 16.0d, $$5 / 16.0d);
    }

    public static VoxelShape[] boxes(int $$0, IntFunction<VoxelShape> $$1) {
        return (VoxelShape[]) IntStream.rangeClosed(0, $$0).mapToObj($$1).toArray($$02 -> {
            return new VoxelShape[$$02];
        });
    }

    public static VoxelShape cube(double $$0) {
        return cube($$0, $$0, $$0);
    }

    public static VoxelShape cube(double $$0, double $$1, double $$2) {
        double $$3 = $$1 / 2.0d;
        return column($$0, $$2, 8.0d - $$3, 8.0d + $$3);
    }

    public static VoxelShape column(double $$0, double $$1, double $$2) {
        return column($$0, $$0, $$1, $$2);
    }

    public static VoxelShape column(double $$0, double $$1, double $$2, double $$3) {
        double $$4 = $$0 / 2.0d;
        double $$5 = $$1 / 2.0d;
        return box(8.0d - $$4, $$2, 8.0d - $$5, 8.0d + $$4, $$3, 8.0d + $$5);
    }

    public static VoxelShape boxZ(double $$0, double $$1, double $$2) {
        return boxZ($$0, $$0, $$1, $$2);
    }

    public static VoxelShape boxZ(double $$0, double $$1, double $$2, double $$3) {
        double $$4 = $$1 / 2.0d;
        return boxZ($$0, 8.0d - $$4, 8.0d + $$4, $$2, $$3);
    }

    public static VoxelShape boxZ(double $$0, double $$1, double $$2, double $$3, double $$4) {
        double $$5 = $$0 / 2.0d;
        return box(8.0d - $$5, $$1, $$3, 8.0d + $$5, $$2, $$4);
    }

    public static BlockState updateFromNeighbourShapes(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
        BlockState $$3 = $$0;
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Direction $$5 : UPDATE_SHAPE_ORDER) {
            $$4.setWithOffset($$2, $$5);
            $$3 = $$3.updateShape($$1, $$1, $$2, $$5, $$4, $$1.getBlockState($$4), $$1.getRandom());
        }
        return $$3;
    }

    public static void updateOrDestroy(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, @UpdateFlags int $$4) {
        updateOrDestroy($$0, $$1, $$2, $$3, $$4, 512);
    }

    public static void updateOrDestroy(BlockState $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, @UpdateFlags int $$4, int $$5) {
        if ($$1 != $$0) {
            if ($$1.isAir()) {
                if (!$$2.isClientSide()) {
                    $$2.destroyBlock($$3, ($$4 & 32) == 0, null, $$5);
                    return;
                }
                return;
            }
            $$2.setBlock($$3, $$1, $$4 & (-33), $$5);
        }
    }

    public Block(BlockBehaviour.Properties $$0) {
        super($$0);
        this.builtInRegistryHolder = BuiltInRegistries.BLOCK.createIntrusiveHolder(this);
        StateDefinition.Builder<Block, BlockState> $$1 = new StateDefinition.Builder<>(this);
        createBlockStateDefinition($$1);
        this.stateDefinition = $$1.create((v0) -> {
            return v0.defaultBlockState();
        }, BlockState::new);
        registerDefaultState((BlockState) this.stateDefinition.any());
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            String $$2 = getClass().getSimpleName();
            if (!$$2.endsWith("Block")) {
                LOGGER.error("Block classes should end with Block and {} doesn't.", $$2);
            }
        }
    }

    public static boolean isExceptionForConnection(BlockState $$0) {
        return ($$0.getBlock() instanceof LeavesBlock) || $$0.is(Blocks.BARRIER) || $$0.is(Blocks.CARVED_PUMPKIN) || $$0.is(Blocks.JACK_O_LANTERN) || $$0.is(Blocks.MELON) || $$0.is(Blocks.PUMPKIN) || $$0.is(BlockTags.SHULKER_BOXES);
    }

    protected static boolean dropFromBlockInteractLootTable(ServerLevel $$0, ResourceKey<LootTable> $$1, BlockState $$2, BlockEntity $$3, ItemStack $$4, Entity $$5, BiConsumer<ServerLevel, ItemStack> $$6) {
        return dropFromLootTable($$0, $$1, $$42 -> {
            return $$42.withParameter(LootContextParams.BLOCK_STATE, $$2).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$3).withOptionalParameter(LootContextParams.INTERACTING_ENTITY, $$5).withOptionalParameter(LootContextParams.TOOL, $$4).create(LootContextParamSets.BLOCK_INTERACT);
        }, $$6);
    }

    protected static boolean dropFromLootTable(ServerLevel $$0, ResourceKey<LootTable> $$1, Function<LootParams.Builder, LootParams> $$2, BiConsumer<ServerLevel, ItemStack> $$3) {
        LootTable $$4 = $$0.getServer().reloadableRegistries().getLootTable($$1);
        LootParams $$5 = $$2.apply(new LootParams.Builder($$0));
        ObjectArrayList<ItemStack> randomItems = $$4.getRandomItems($$5);
        if (!randomItems.isEmpty()) {
            randomItems.forEach($$22 -> {
                $$3.accept($$0, $$22);
            });
            return true;
        }
        return false;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/Block$ShapePairKey.class */
    static final class ShapePairKey extends Record {
        private final VoxelShape first;
        private final VoxelShape second;

        ShapePairKey(VoxelShape $$0, VoxelShape $$1) {
            this.first = $$0;
            this.second = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ShapePairKey.class), ShapePairKey.class, "first;second", "FIELD:Lnet/minecraft/world/level/block/Block$ShapePairKey;->first:Lnet/minecraft/world/phys/shapes/VoxelShape;", "FIELD:Lnet/minecraft/world/level/block/Block$ShapePairKey;->second:Lnet/minecraft/world/phys/shapes/VoxelShape;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public VoxelShape first() {
            return this.first;
        }

        public VoxelShape second() {
            return this.second;
        }

        @Override // java.lang.Record
        public boolean equals(Object $$0) {
            if ($$0 instanceof ShapePairKey) {
                ShapePairKey $$1 = (ShapePairKey) $$0;
                if (this.first == $$1.first && this.second == $$1.second) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.lang.Record
        public int hashCode() {
            return (System.identityHashCode(this.first) * 31) + System.identityHashCode(this.second);
        }
    }

    public static boolean shouldRenderFace(BlockState $$0, BlockState $$1, Direction $$2) {
        VoxelShape $$4;
        VoxelShape $$3 = $$1.getFaceOcclusionShape($$2.getOpposite());
        if ($$3 == Shapes.block() || $$0.skipRendering($$1, $$2)) {
            return false;
        }
        if ($$3 == Shapes.empty() || ($$4 = $$0.getFaceOcclusionShape($$2)) == Shapes.empty()) {
            return true;
        }
        ShapePairKey $$5 = new ShapePairKey($$4, $$3);
        Object2ByteLinkedOpenHashMap<ShapePairKey> $$6 = OCCLUSION_CACHE.get();
        byte $$7 = $$6.getAndMoveToFirst($$5);
        if ($$7 != 127) {
            return $$7 != 0;
        }
        boolean $$8 = Shapes.joinIsNotEmpty($$4, $$3, BooleanOp.ONLY_FIRST);
        if ($$6.size() == 256) {
            $$6.removeLastByte();
        }
        $$6.putAndMoveToFirst($$5, (byte) ($$8 ? 1 : 0));
        return $$8;
    }

    public static boolean canSupportRigidBlock(BlockGetter $$0, BlockPos $$1) {
        return $$0.getBlockState($$1).isFaceSturdy($$0, $$1, Direction.UP, SupportType.RIGID);
    }

    public static boolean canSupportCenter(LevelReader $$0, BlockPos $$1, Direction $$2) {
        BlockState $$3 = $$0.getBlockState($$1);
        if ($$2 == Direction.DOWN && $$3.is(BlockTags.UNSTABLE_BOTTOM_CENTER)) {
            return false;
        }
        return $$3.isFaceSturdy($$0, $$1, $$2, SupportType.CENTER);
    }

    public static boolean isFaceFull(VoxelShape $$0, Direction $$1) {
        VoxelShape $$2 = $$0.getFaceShape($$1);
        return isShapeFullBlock($$2);
    }

    public static boolean isShapeFullBlock(VoxelShape $$0) {
        return ((Boolean) SHAPE_FULL_BLOCK_CACHE.getUnchecked($$0)).booleanValue();
    }

    public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
    }

    public void destroy(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
    }

    public static List<ItemStack> getDrops(BlockState $$0, ServerLevel $$1, BlockPos $$2, BlockEntity $$3) {
        LootParams.Builder $$4 = new LootParams.Builder($$1).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf($$2)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$3);
        return $$0.getDrops($$4);
    }

    public static List<ItemStack> getDrops(BlockState $$0, ServerLevel $$1, BlockPos $$2, BlockEntity $$3, Entity $$4, ItemStack $$5) {
        LootParams.Builder $$6 = new LootParams.Builder($$1).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf($$2)).withParameter(LootContextParams.TOOL, $$5).withOptionalParameter(LootContextParams.THIS_ENTITY, $$4).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$3);
        return $$0.getDrops($$6);
    }

    public static void dropResources(BlockState $$0, Level $$1, BlockPos $$2) {
        if ($$1 instanceof ServerLevel) {
            getDrops($$0, (ServerLevel) $$1, $$2, null).forEach($$22 -> {
                popResource($$1, $$2, $$22);
            });
            $$0.spawnAfterBreak((ServerLevel) $$1, $$2, ItemStack.EMPTY, true);
        }
    }

    public static void dropResources(BlockState $$0, LevelAccessor $$1, BlockPos $$2, BlockEntity $$3) {
        if ($$1 instanceof ServerLevel) {
            getDrops($$0, (ServerLevel) $$1, $$2, $$3).forEach($$22 -> {
                popResource((ServerLevel) $$1, $$2, $$22);
            });
            $$0.spawnAfterBreak((ServerLevel) $$1, $$2, ItemStack.EMPTY, true);
        }
    }

    public static void dropResources(BlockState $$0, Level $$1, BlockPos $$2, BlockEntity $$3, Entity $$4, ItemStack $$5) {
        if ($$1 instanceof ServerLevel) {
            getDrops($$0, (ServerLevel) $$1, $$2, $$3, $$4, $$5).forEach($$22 -> {
                popResource($$1, $$2, $$22);
            });
            $$0.spawnAfterBreak((ServerLevel) $$1, $$2, $$5, true);
        }
    }

    public static void popResource(Level $$0, BlockPos $$1, ItemStack $$2) {
        double $$3 = ((double) EntityType.ITEM.getHeight()) / 2.0d;
        double $$4 = ((double) $$1.getX()) + 0.5d + Mth.nextDouble($$0.random, -0.25d, 0.25d);
        double $$5 = ((((double) $$1.getY()) + 0.5d) + Mth.nextDouble($$0.random, -0.25d, 0.25d)) - $$3;
        double $$6 = ((double) $$1.getZ()) + 0.5d + Mth.nextDouble($$0.random, -0.25d, 0.25d);
        popResource($$0, (Supplier<ItemEntity>) () -> {
            return new ItemEntity($$0, $$4, $$5, $$6, $$2);
        }, $$2);
    }

    public static void popResourceFromFace(Level $$0, BlockPos $$1, Direction $$2, ItemStack $$3) {
        int $$4 = $$2.getStepX();
        int $$5 = $$2.getStepY();
        int $$6 = $$2.getStepZ();
        double $$7 = ((double) EntityType.ITEM.getWidth()) / 2.0d;
        double $$8 = ((double) EntityType.ITEM.getHeight()) / 2.0d;
        double $$9 = ((double) $$1.getX()) + 0.5d + ($$4 == 0 ? Mth.nextDouble($$0.random, -0.25d, 0.25d) : ((double) $$4) * (0.5d + $$7));
        double $$10 = ((((double) $$1.getY()) + 0.5d) + ($$5 == 0 ? Mth.nextDouble($$0.random, -0.25d, 0.25d) : ((double) $$5) * (0.5d + $$8))) - $$8;
        double $$11 = ((double) $$1.getZ()) + 0.5d + ($$6 == 0 ? Mth.nextDouble($$0.random, -0.25d, 0.25d) : ((double) $$6) * (0.5d + $$7));
        double $$12 = $$4 == 0 ? Mth.nextDouble($$0.random, -0.1d, 0.1d) : ((double) $$4) * 0.1d;
        double $$13 = $$5 == 0 ? Mth.nextDouble($$0.random, Density.SURFACE, 0.1d) : (((double) $$5) * 0.1d) + 0.1d;
        double $$14 = $$6 == 0 ? Mth.nextDouble($$0.random, -0.1d, 0.1d) : ((double) $$6) * 0.1d;
        popResource($$0, (Supplier<ItemEntity>) () -> {
            return new ItemEntity($$0, $$9, $$10, $$11, $$3, $$12, $$13, $$14);
        }, $$3);
    }

    private static void popResource(Level $$0, Supplier<ItemEntity> $$1, ItemStack $$2) {
        if ($$0 instanceof ServerLevel) {
            ServerLevel $$3 = (ServerLevel) $$0;
            if ($$2.isEmpty() || !((Boolean) $$3.getGameRules().get(GameRules.BLOCK_DROPS)).booleanValue()) {
                return;
            }
            ItemEntity $$5 = $$1.get();
            $$5.setDefaultPickUpDelay();
            $$0.addFreshEntity($$5);
        }
    }

    protected void popExperience(ServerLevel $$0, BlockPos $$1, int $$2) {
        if (((Boolean) $$0.getGameRules().get(GameRules.BLOCK_DROPS)).booleanValue()) {
            ExperienceOrb.award($$0, Vec3.atCenterOf($$1), $$2);
        }
    }

    public float getExplosionResistance() {
        return this.explosionResistance;
    }

    public void wasExploded(ServerLevel $$0, BlockPos $$1, Explosion $$2) {
    }

    public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
    }

    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return defaultBlockState();
    }

    public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, BlockEntity $$4, ItemStack $$5) {
        $$1.awardStat(Stats.BLOCK_MINED.get(this));
        $$1.causeFoodExhaustion(0.005f);
        dropResources($$3, $$0, $$2, $$4, $$1, $$5);
    }

    public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
    }

    public boolean isPossibleToRespawnInThis(BlockState $$0) {
        return ($$0.isSolid() || $$0.liquid()) ? false : true;
    }

    public MutableComponent getName() {
        return Component.translatable(getDescriptionId());
    }

    public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, double $$4) {
        $$3.causeFallDamage($$4, 1.0f, $$3.damageSources().fall());
    }

    public void updateEntityMovementAfterFallOn(BlockGetter $$0, Entity $$1) {
        $$1.setDeltaMovement($$1.getDeltaMovement().multiply(1.0d, Density.SURFACE, 1.0d));
    }

    public float getFriction() {
        return this.friction;
    }

    public float getSpeedFactor() {
        return this.speedFactor;
    }

    public float getJumpFactor() {
        return this.jumpFactor;
    }

    protected void spawnDestroyParticles(Level $$0, Player $$1, BlockPos $$2, BlockState $$3) {
        $$0.levelEvent($$1, LevelEvent.PARTICLES_DESTROY_BLOCK, $$2, getId($$3));
    }

    public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
        spawnDestroyParticles($$0, $$3, $$1, $$2);
        if ($$2.is(BlockTags.GUARDED_BY_PIGLINS) && ($$0 instanceof ServerLevel)) {
            ServerLevel $$4 = (ServerLevel) $$0;
            PiglinAi.angerNearbyPiglins($$4, $$3, false);
        }
        $$0.gameEvent(GameEvent.BLOCK_DESTROY, $$1, GameEvent.Context.of($$3, $$2));
        return $$2;
    }

    public void handlePrecipitation(BlockState $$0, Level $$1, BlockPos $$2, Biome.Precipitation $$3) {
    }

    public boolean dropFromExplosion(Explosion $$0) {
        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
    }

    public StateDefinition<Block, BlockState> getStateDefinition() {
        return this.stateDefinition;
    }

    protected final void registerDefaultState(BlockState $$0) {
        this.defaultBlockState = $$0;
    }

    public final BlockState defaultBlockState() {
        return this.defaultBlockState;
    }

    public final BlockState withPropertiesOf(BlockState $$0) {
        BlockState $$1 = defaultBlockState();
        for (Property<?> $$2 : $$0.getBlock().getStateDefinition().getProperties()) {
            if ($$1.hasProperty($$2)) {
                $$1 = copyProperty($$0, $$1, $$2);
            }
        }
        return $$1;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState $$0, BlockState $$1, Property<T> $$2) {
        return (BlockState) $$1.setValue($$2, $$0.getValue($$2));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour, net.minecraft.world.level.ItemLike
    public Item asItem() {
        if (this.item == null) {
            this.item = Item.byBlock(this);
        }
        return this.item;
    }

    public boolean hasDynamicShape() {
        return this.dynamicShape;
    }

    public String toString() {
        return "Block{" + BuiltInRegistries.BLOCK.wrapAsHolder(this).getRegisteredName() + "}";
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected Block asBlock() {
        return this;
    }

    protected Function<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> $$0) {
        ImmutableMap immutableMap = (ImmutableMap) this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), $$0));
        Objects.requireNonNull(immutableMap);
        return (v1) -> {
            return r0.get(v1);
        };
    }

    protected Function<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> $$0, Property<?>... $$1) {
        Map<? extends Property<?>, Object> $$2 = (Map) Arrays.stream($$1).collect(Collectors.toMap($$02 -> {
            return $$02;
        }, $$03 -> {
            return $$03.getPossibleValues().getFirst();
        }));
        ImmutableMap<BlockState, VoxelShape> $$3 = (ImmutableMap) this.stateDefinition.getPossibleStates().stream().filter($$12 -> {
            return $$2.entrySet().stream().allMatch($$12 -> {
                return $$12.getValue((Property) $$12.getKey()) == $$12.getValue();
            });
        }).collect(ImmutableMap.toImmutableMap(Function.identity(), $$0));
        return $$22 -> {
            for (Map.Entry<? extends Property<?>, Object> $$32 : $$2.entrySet()) {
                $$22 = (BlockState) setValueHelper($$22, (Property) $$32.getKey(), $$32.getValue());
            }
            return (VoxelShape) $$3.get($$22);
        };
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S $$0, Property<T> $$1, Object $$2) {
        return (S) $$0.setValue($$1, (Comparable) $$2);
    }

    @Deprecated
    public Holder.Reference<Block> builtInRegistryHolder() {
        return this.builtInRegistryHolder;
    }

    protected void tryDropExperience(ServerLevel $$0, BlockPos $$1, ItemStack $$2, IntProvider $$3) {
        int $$4 = EnchantmentHelper.processBlockExperience($$0, $$2, $$3.sample($$0.getRandom()));
        if ($$4 > 0) {
            popExperience($$0, $$1, $$4);
        }
    }
}
