package net.minecraft.gametest.framework;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.FillBiomeCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestHelper.class */
public class GameTestHelper {
    private final GameTestInfo testInfo;
    private boolean finalCheckAdded;

    public GameTestHelper(GameTestInfo $$0) {
        this.testInfo = $$0;
    }

    public GameTestAssertException assertionException(Component $$0) {
        return new GameTestAssertException($$0, this.testInfo.getTick());
    }

    public GameTestAssertException assertionException(String $$0, Object... $$1) {
        return assertionException(Component.translatableEscape($$0, $$1));
    }

    public GameTestAssertPosException assertionException(BlockPos $$0, Component $$1) {
        return new GameTestAssertPosException($$1, absolutePos($$0), $$0, this.testInfo.getTick());
    }

    public GameTestAssertPosException assertionException(BlockPos $$0, String $$1, Object... $$2) {
        return assertionException($$0, Component.translatableEscape($$1, $$2));
    }

    public ServerLevel getLevel() {
        return this.testInfo.getLevel();
    }

    public BlockState getBlockState(BlockPos $$0) {
        return getLevel().getBlockState(absolutePos($$0));
    }

    public <T extends BlockEntity> T getBlockEntity(BlockPos $$0, Class<T> $$1) {
        BlockEntity $$2 = getLevel().getBlockEntity(absolutePos($$0));
        if ($$2 == null) {
            throw assertionException($$0, "test.error.missing_block_entity", new Object[0]);
        }
        if ($$1.isInstance($$2)) {
            return $$1.cast($$2);
        }
        throw assertionException($$0, "test.error.wrong_block_entity", $$2.getType().builtInRegistryHolder().getRegisteredName());
    }

    public void killAllEntities() {
        killAllEntitiesOfClass(Entity.class);
    }

    public void killAllEntitiesOfClass(Class<? extends Entity> $$0) {
        AABB $$1 = getBounds();
        List<? extends Entity> $$2 = getLevel().getEntitiesOfClass($$0, $$1.inflate(1.0d), $$02 -> {
            return !($$02 instanceof Player);
        });
        $$2.forEach($$03 -> {
            $$03.kill(getLevel());
        });
    }

    public ItemEntity spawnItem(Item $$0, Vec3 $$1) {
        ServerLevel $$2 = getLevel();
        Vec3 $$3 = absoluteVec($$1);
        ItemEntity $$4 = new ItemEntity($$2, $$3.x, $$3.y, $$3.z, new ItemStack($$0, 1));
        $$4.setDeltaMovement(Density.SURFACE, Density.SURFACE, Density.SURFACE);
        $$2.addFreshEntity($$4);
        return $$4;
    }

    public ItemEntity spawnItem(Item $$0, float $$1, float $$2, float $$3) {
        return spawnItem($$0, new Vec3($$1, $$2, $$3));
    }

    public ItemEntity spawnItem(Item $$0, BlockPos $$1) {
        return spawnItem($$0, $$1.getX(), $$1.getY(), $$1.getZ());
    }

    public <E extends Entity> E spawn(EntityType<E> entityType, BlockPos blockPos) {
        return (E) spawn(entityType, Vec3.atBottomCenterOf(blockPos));
    }

    public <E extends Entity> List<E> spawn(EntityType<E> $$0, BlockPos $$1, int $$2) {
        return spawn($$0, Vec3.atBottomCenterOf($$1), $$2);
    }

    public <E extends Entity> List<E> spawn(EntityType<E> $$0, Vec3 $$1, int $$2) {
        ArrayList arrayList = new ArrayList();
        for (int $$4 = 0; $$4 < $$2; $$4++) {
            arrayList.add(spawn($$0, $$1));
        }
        return arrayList;
    }

    public <E extends Entity> E spawn(EntityType<E> entityType, Vec3 vec3) {
        return (E) spawn(entityType, vec3, (EntitySpawnReason) null);
    }

    public <E extends Entity> E spawn(EntityType<E> entityType, Vec3 vec3, EntitySpawnReason entitySpawnReason) {
        ServerLevel level = getLevel();
        Mob mob = (E) entityType.create(level, EntitySpawnReason.STRUCTURE);
        if (mob == null) {
            throw assertionException(BlockPos.containing(vec3), "test.error.spawn_failure", entityType.builtInRegistryHolder().getRegisteredName());
        }
        if (mob instanceof Mob) {
            mob.setPersistenceRequired();
        }
        Vec3 vec3AbsoluteVec = absoluteVec(vec3);
        float fRotate = mob.rotate(getTestRotation());
        mob.snapTo(vec3AbsoluteVec.x, vec3AbsoluteVec.y, vec3AbsoluteVec.z, fRotate, mob.getXRot());
        mob.setYBodyRot(fRotate);
        mob.setYHeadRot(fRotate);
        if (entitySpawnReason != null && (mob instanceof Mob)) {
            Mob mob2 = mob;
            mob2.finalizeSpawn(getLevel(), getLevel().getCurrentDifficultyAt(mob2.blockPosition()), entitySpawnReason, null);
        }
        level.addFreshEntityWithPassengers(mob);
        return mob;
    }

    public <E extends Mob> E spawn(EntityType<E> $$0, int $$1, int $$2, int $$3, EntitySpawnReason $$4) {
        return (E) spawn($$0, new Vec3($$1, $$2, $$3), $$4);
    }

    public void hurt(Entity $$0, DamageSource $$1, float $$2) {
        $$0.hurtServer(getLevel(), $$1, $$2);
    }

    public void kill(Entity $$0) {
        $$0.kill(getLevel());
    }

    public <E extends Entity> E findOneEntity(EntityType<E> entityType) {
        return (E) findClosestEntity(entityType, 0, 0, 0, 2.147483647E9d);
    }

    public <E extends Entity> E findClosestEntity(EntityType<E> $$0, int $$1, int $$2, int $$3, double $$4) {
        List<E> $$5 = findEntities($$0, $$1, $$2, $$3, $$4);
        if ($$5.isEmpty()) {
            throw assertionException("test.error.expected_entity_around", $$0.getDescription(), Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3));
        }
        if ($$5.size() > 1) {
            throw assertionException("test.error.too_many_entities", $$0.toShortString(), Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$5.size()));
        }
        Vec3 $$6 = absoluteVec(new Vec3($$1, $$2, $$3));
        $$5.sort(($$12, $$22) -> {
            double $$32 = $$12.position().distanceTo($$6);
            double $$42 = $$22.position().distanceTo($$6);
            return Double.compare($$32, $$42);
        });
        return $$5.get(0);
    }

    public <E extends Entity> List<E> findEntities(EntityType<E> $$0, int $$1, int $$2, int $$3, double $$4) {
        return findEntities($$0, Vec3.atBottomCenterOf(new BlockPos($$1, $$2, $$3)), $$4);
    }

    public <E extends Entity> List<E> findEntities(EntityType<E> $$0, Vec3 $$1, double $$2) {
        ServerLevel $$3 = getLevel();
        Vec3 $$4 = absoluteVec($$1);
        AABB $$5 = this.testInfo.getStructureBounds();
        AABB $$6 = new AABB($$4.add(-$$2, -$$2, -$$2), $$4.add($$2, $$2, $$2));
        return $$3.getEntities($$0, $$5, $$12 -> {
            return $$12.getBoundingBox().intersects($$6) && $$12.isAlive();
        });
    }

    public <E extends Entity> E spawn(EntityType<E> entityType, int i, int i2, int i3) {
        return (E) spawn(entityType, new BlockPos(i, i2, i3));
    }

    public <E extends Entity> E spawn(EntityType<E> entityType, float f, float f2, float f3) {
        return (E) spawn(entityType, new Vec3(f, f2, f3));
    }

    public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, BlockPos $$1) {
        E e = (E) spawn($$0, $$1);
        e.removeFreeWill();
        return e;
    }

    public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> entityType, int i, int i2, int i3) {
        return (E) spawnWithNoFreeWill(entityType, new BlockPos(i, i2, i3));
    }

    public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, Vec3 $$1) {
        E e = (E) spawn($$0, $$1);
        e.removeFreeWill();
        return e;
    }

    public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> entityType, float f, float f2, float f3) {
        return (E) spawnWithNoFreeWill(entityType, new Vec3(f, f2, f3));
    }

    public void moveTo(Mob $$0, float $$1, float $$2, float $$3) {
        Vec3 $$4 = absoluteVec(new Vec3($$1, $$2, $$3));
        $$0.snapTo($$4.x, $$4.y, $$4.z, $$0.getYRot(), $$0.getXRot());
    }

    public GameTestSequence walkTo(Mob $$0, BlockPos $$1, float $$2) {
        return startSequence().thenExecuteAfter(2, () -> {
            Path $$3 = $$0.getNavigation().createPath(absolutePos($$1), 0);
            $$0.getNavigation().moveTo($$3, $$2);
        });
    }

    public void pressButton(int $$0, int $$1, int $$2) {
        pressButton(new BlockPos($$0, $$1, $$2));
    }

    public void pressButton(BlockPos $$0) {
        assertBlockTag(BlockTags.BUTTONS, $$0);
        BlockPos $$1 = absolutePos($$0);
        BlockState $$2 = getLevel().getBlockState($$1);
        ButtonBlock $$3 = (ButtonBlock) $$2.getBlock();
        $$3.press($$2, getLevel(), $$1, null);
    }

    public void useBlock(BlockPos $$0) {
        useBlock($$0, makeMockPlayer(GameType.CREATIVE));
    }

    public void useBlock(BlockPos $$0, Player $$1) {
        BlockPos $$2 = absolutePos($$0);
        useBlock($$0, $$1, new BlockHitResult(Vec3.atCenterOf($$2), Direction.NORTH, $$2, true));
    }

    public void useBlock(BlockPos $$0, Player $$1, BlockHitResult $$2) {
        BlockPos $$3 = absolutePos($$0);
        BlockState $$4 = getLevel().getBlockState($$3);
        InteractionHand $$5 = InteractionHand.MAIN_HAND;
        InteractionResult $$6 = $$4.useItemOn($$1.getItemInHand($$5), getLevel(), $$1, $$5, $$2);
        if ($$6.consumesAction()) {
            return;
        }
        if (($$6 instanceof InteractionResult.TryEmptyHandInteraction) && $$4.useWithoutItem(getLevel(), $$1, $$2).consumesAction()) {
            return;
        }
        UseOnContext $$7 = new UseOnContext($$1, $$5, $$2);
        $$1.getItemInHand($$5).useOn($$7);
    }

    public LivingEntity makeAboutToDrown(LivingEntity $$0) {
        $$0.setAirSupply(0);
        $$0.setHealth(0.25f);
        return $$0;
    }

    public LivingEntity withLowHealth(LivingEntity $$0) {
        $$0.setHealth(0.25f);
        return $$0;
    }

    public Player makeMockPlayer(final GameType $$0) {
        return new Player(this, getLevel(), new GameProfile(UUID.randomUUID(), "test-mock-player")) { // from class: net.minecraft.gametest.framework.GameTestHelper.1
            @Override // net.minecraft.world.entity.player.Player
            public GameType gameMode() {
                return $$0;
            }

            @Override // net.minecraft.world.entity.player.Player, net.minecraft.world.entity.Entity
            public boolean isClientAuthoritative() {
                return false;
            }
        };
    }

    @Deprecated(forRemoval = true)
    public ServerPlayer makeMockServerPlayerInLevel() {
        CommonListenerCookie $$0 = CommonListenerCookie.createInitial(new GameProfile(UUID.randomUUID(), "test-mock-player"), false);
        ServerPlayer $$1 = new ServerPlayer(this, getLevel().getServer(), getLevel(), $$0.gameProfile(), $$0.clientInformation()) { // from class: net.minecraft.gametest.framework.GameTestHelper.2
            @Override // net.minecraft.server.level.ServerPlayer, net.minecraft.world.entity.player.Player
            public GameType gameMode() {
                return GameType.CREATIVE;
            }
        };
        ChannelHandler connection = new Connection(PacketFlow.SERVERBOUND);
        new EmbeddedChannel(new ChannelHandler[]{connection});
        getLevel().getServer().getPlayerList().placeNewPlayer(connection, $$1, $$0);
        return $$1;
    }

    public void pullLever(int $$0, int $$1, int $$2) {
        pullLever(new BlockPos($$0, $$1, $$2));
    }

    public void pullLever(BlockPos $$0) {
        assertBlockPresent(Blocks.LEVER, $$0);
        BlockPos $$1 = absolutePos($$0);
        BlockState $$2 = getLevel().getBlockState($$1);
        LeverBlock $$3 = (LeverBlock) $$2.getBlock();
        $$3.pull($$2, getLevel(), $$1, null);
    }

    public void pulseRedstone(BlockPos $$0, long $$1) {
        setBlock($$0, Blocks.REDSTONE_BLOCK);
        runAfterDelay($$1, () -> {
            setBlock($$0, Blocks.AIR);
        });
    }

    public void destroyBlock(BlockPos $$0) {
        getLevel().destroyBlock(absolutePos($$0), false, null);
    }

    public void setBlock(int $$0, int $$1, int $$2, Block $$3) {
        setBlock(new BlockPos($$0, $$1, $$2), $$3);
    }

    public void setBlock(int $$0, int $$1, int $$2, BlockState $$3) {
        setBlock(new BlockPos($$0, $$1, $$2), $$3);
    }

    public void setBlock(BlockPos $$0, Block $$1) {
        setBlock($$0, $$1.defaultBlockState());
    }

    public void setBlock(BlockPos $$0, BlockState $$1) {
        getLevel().setBlock(absolutePos($$0), $$1, 3);
    }

    public void setBlock(BlockPos $$0, Block $$1, Direction $$2) {
        setBlock($$0, $$1.defaultBlockState(), $$2);
    }

    public void setBlock(BlockPos $$0, BlockState $$1, Direction $$2) {
        BlockState $$3 = $$1;
        if ($$1.hasProperty(HorizontalDirectionalBlock.FACING)) {
            $$3 = (BlockState) $$1.setValue(HorizontalDirectionalBlock.FACING, $$2);
        }
        if ($$1.hasProperty(BlockStateProperties.FACING)) {
            $$3 = (BlockState) $$1.setValue(BlockStateProperties.FACING, $$2);
        }
        getLevel().setBlock(absolutePos($$0), $$3, 3);
    }

    public void assertBlockPresent(Block $$0, int $$1, int $$2, int $$3) {
        assertBlockPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void assertBlockPresent(Block $$0, BlockPos $$1) {
        BlockState $$2 = getBlockState($$1);
        assertBlock($$1, $$22 -> {
            return $$2.is($$0);
        }, $$12 -> {
            return Component.translatable("test.error.expected_block", $$0.getName(), $$12.getName());
        });
    }

    public void assertBlockNotPresent(Block $$0, int $$1, int $$2, int $$3) {
        assertBlockNotPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void assertBlockNotPresent(Block $$0, BlockPos $$1) {
        assertBlock($$1, $$2 -> {
            return !getBlockState($$1).is($$0);
        }, $$12 -> {
            return Component.translatable("test.error.unexpected_block", $$0.getName());
        });
    }

    public void assertBlockTag(TagKey<Block> $$0, BlockPos $$1) {
        assertBlockState($$1, $$12 -> {
            return $$12.is((TagKey<Block>) $$0);
        }, $$13 -> {
            return Component.translatable("test.error.expected_block_tag", Component.translationArg($$0.location()), $$13.getBlock().getName());
        });
    }

    public void succeedWhenBlockPresent(Block $$0, int $$1, int $$2, int $$3) {
        succeedWhenBlockPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void succeedWhenBlockPresent(Block $$0, BlockPos $$1) {
        succeedWhen(() -> {
            assertBlockPresent($$0, $$1);
        });
    }

    public void assertBlock(BlockPos $$0, Predicate<Block> $$1, Function<Block, Component> $$2) {
        assertBlockState($$0, $$12 -> {
            return $$1.test($$12.getBlock());
        }, $$13 -> {
            return (Component) $$2.apply($$13.getBlock());
        });
    }

    public <T extends Comparable<T>> void assertBlockProperty(BlockPos $$0, Property<T> $$1, T $$2) {
        BlockState $$3 = getBlockState($$0);
        boolean $$4 = $$3.hasProperty($$1);
        if (!$$4) {
            throw assertionException($$0, "test.error.block_property_missing", $$1.getName(), $$2);
        }
        if (!$$3.getValue($$1).equals($$2)) {
            throw assertionException($$0, "test.error.block_property_mismatch", $$1.getName(), $$2, $$3.getValue($$1));
        }
    }

    public <T extends Comparable<T>> void assertBlockProperty(BlockPos $$0, Property<T> $$1, Predicate<T> $$2, Component $$3) {
        assertBlockState($$0, $$22 -> {
            if (!$$22.hasProperty($$1)) {
                return false;
            }
            return $$2.test($$22.getValue($$1));
        }, $$12 -> {
            return $$3;
        });
    }

    public void assertBlockState(BlockPos $$0, BlockState $$1) {
        BlockState $$2 = getBlockState($$0);
        if (!$$2.equals($$1)) {
            throw assertionException($$0, "test.error.state_not_equal", $$1, $$2);
        }
    }

    public void assertBlockState(BlockPos $$0, Predicate<BlockState> $$1, Function<BlockState, Component> $$2) {
        BlockState $$3 = getBlockState($$0);
        if (!$$1.test($$3)) {
            throw assertionException($$0, $$2.apply($$3));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends BlockEntity> void assertBlockEntityData(BlockPos $$0, Class<T> $$1, Predicate<T> predicate, Supplier<Component> $$3) {
        if (!predicate.test(getBlockEntity($$0, $$1))) {
            throw assertionException($$0, $$3.get());
        }
    }

    public void assertRedstoneSignal(BlockPos $$0, Direction $$1, IntPredicate $$2, Supplier<Component> $$3) {
        BlockPos $$4 = absolutePos($$0);
        ServerLevel $$5 = getLevel();
        BlockState $$6 = $$5.getBlockState($$4);
        int $$7 = $$6.getSignal($$5, $$4, $$1);
        if (!$$2.test($$7)) {
            throw assertionException($$0, $$3.get());
        }
    }

    public void assertEntityPresent(EntityType<?> $$0) {
        if (!getLevel().hasEntities($$0, getBounds(), (v0) -> {
            return v0.isAlive();
        })) {
            throw assertionException("test.error.expected_entity_in_test", $$0.getDescription());
        }
    }

    public void assertEntityPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
        assertEntityPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void assertEntityPresent(EntityType<?> $$0, BlockPos $$1) {
        BlockPos $$2 = absolutePos($$1);
        if (!getLevel().hasEntities($$0, new AABB($$2), (v0) -> {
            return v0.isAlive();
        })) {
            throw assertionException($$1, "test.error.expected_entity", $$0.getDescription());
        }
    }

    public void assertEntityPresent(EntityType<?> $$0, AABB $$1) {
        AABB $$2 = absoluteAABB($$1);
        if (!getLevel().hasEntities($$0, $$2, (v0) -> {
            return v0.isAlive();
        })) {
            throw assertionException(BlockPos.containing($$1.getCenter()), "test.error.expected_entity", $$0.getDescription());
        }
    }

    public void assertEntityPresent(EntityType<?> $$0, AABB $$1, Component $$2) {
        AABB $$3 = absoluteAABB($$1);
        if (!getLevel().hasEntities($$0, $$3, (v0) -> {
            return v0.isAlive();
        })) {
            throw assertionException(BlockPos.containing($$1.getCenter()), $$2);
        }
    }

    public void assertEntitiesPresent(EntityType<?> $$0, int $$1) {
        List<? extends Entity> $$2 = getLevel().getEntities($$0, getBounds(), (v0) -> {
            return v0.isAlive();
        });
        if ($$2.size() != $$1) {
            throw assertionException("test.error.expected_entity_count", Integer.valueOf($$1), $$0.getDescription(), Integer.valueOf($$2.size()));
        }
    }

    public void assertEntitiesPresent(EntityType<?> $$0, BlockPos $$1, int $$2, double $$3) {
        absolutePos($$1);
        List<? extends Entity> $$5 = getEntities($$0, $$1, $$3);
        if ($$5.size() != $$2) {
            throw assertionException($$1, "test.error.expected_entity_count", Integer.valueOf($$2), $$0.getDescription(), Integer.valueOf($$5.size()));
        }
    }

    public void assertEntityPresent(EntityType<?> $$0, BlockPos $$1, double $$2) {
        List<? extends Entity> $$3 = getEntities($$0, $$1, $$2);
        if ($$3.isEmpty()) {
            absolutePos($$1);
            throw assertionException($$1, "test.error.expected_entity", $$0.getDescription());
        }
    }

    public <T extends Entity> List<T> getEntities(EntityType<T> $$0, BlockPos $$1, double $$2) {
        BlockPos $$3 = absolutePos($$1);
        return getLevel().getEntities($$0, new AABB($$3).inflate($$2), (v0) -> {
            return v0.isAlive();
        });
    }

    public <T extends Entity> List<T> getEntities(EntityType<T> $$0) {
        return getLevel().getEntities($$0, getBounds(), (v0) -> {
            return v0.isAlive();
        });
    }

    public void assertEntityInstancePresent(Entity $$0, int $$1, int $$2, int $$3) throws Throwable {
        assertEntityInstancePresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void assertEntityInstancePresent(Entity $$0, BlockPos $$1) throws Throwable {
        BlockPos $$2 = absolutePos($$1);
        List<? extends Entity> $$3 = getLevel().getEntities($$0.getType(), new AABB($$2), (v0) -> {
            return v0.isAlive();
        });
        $$3.stream().filter($$12 -> {
            return $$12 == $$0;
        }).findFirst().orElseThrow(() -> {
            return assertionException($$1, "test.error.expected_entity", $$0.getType().getDescription());
        });
    }

    public void assertItemEntityCountIs(Item $$0, BlockPos $$1, double $$2, int $$3) {
        BlockPos $$4 = absolutePos($$1);
        List<ItemEntity> $$5 = getLevel().getEntities(EntityType.ITEM, new AABB($$4).inflate($$2), (v0) -> {
            return v0.isAlive();
        });
        int $$6 = 0;
        for (ItemEntity $$7 : $$5) {
            ItemStack $$8 = $$7.getItem();
            if ($$8.is($$0)) {
                $$6 += $$8.getCount();
            }
        }
        if ($$6 != $$3) {
            throw assertionException($$1, "test.error.expected_items_count", Integer.valueOf($$3), $$0.getName(), Integer.valueOf($$6));
        }
    }

    public void assertItemEntityPresent(Item $$0, BlockPos $$1, double $$2) {
        BlockPos $$3 = absolutePos($$1);
        Predicate<ItemEntity> $$4 = $$12 -> {
            return $$12.isAlive() && $$12.getItem().is($$0);
        };
        if (!getLevel().hasEntities(EntityType.ITEM, new AABB($$3).inflate($$2), $$4)) {
            throw assertionException($$1, "test.error.expected_item", $$0.getName());
        }
    }

    public void assertItemEntityNotPresent(Item $$0, BlockPos $$1, double $$2) {
        BlockPos $$3 = absolutePos($$1);
        Predicate<ItemEntity> $$4 = $$12 -> {
            return $$12.isAlive() && $$12.getItem().is($$0);
        };
        if (getLevel().hasEntities(EntityType.ITEM, new AABB($$3).inflate($$2), $$4)) {
            throw assertionException($$1, "test.error.unexpected_item", $$0.getName());
        }
    }

    public void assertItemEntityPresent(Item $$0) {
        Predicate<ItemEntity> $$1 = $$12 -> {
            return $$12.isAlive() && $$12.getItem().is($$0);
        };
        if (!getLevel().hasEntities(EntityType.ITEM, getBounds(), $$1)) {
            throw assertionException("test.error.expected_item", $$0.getName());
        }
    }

    public void assertItemEntityNotPresent(Item $$0) {
        Predicate<ItemEntity> $$1 = $$12 -> {
            return $$12.isAlive() && $$12.getItem().is($$0);
        };
        if (getLevel().hasEntities(EntityType.ITEM, getBounds(), $$1)) {
            throw assertionException("test.error.unexpected_item", $$0.getName());
        }
    }

    public void assertEntityNotPresent(EntityType<?> $$0) {
        List<? extends Entity> $$1 = getLevel().getEntities($$0, getBounds(), (v0) -> {
            return v0.isAlive();
        });
        if (!$$1.isEmpty()) {
            throw assertionException(((Entity) $$1.getFirst()).blockPosition(), "test.error.unexpected_entity", $$0.getDescription());
        }
    }

    public void assertEntityNotPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
        assertEntityNotPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void assertEntityNotPresent(EntityType<?> $$0, BlockPos $$1) {
        BlockPos $$2 = absolutePos($$1);
        if (getLevel().hasEntities($$0, new AABB($$2), (v0) -> {
            return v0.isAlive();
        })) {
            throw assertionException($$1, "test.error.unexpected_entity", $$0.getDescription());
        }
    }

    public void assertEntityNotPresent(EntityType<?> $$0, AABB $$1) {
        AABB $$2 = absoluteAABB($$1);
        List<? extends Entity> $$3 = getLevel().getEntities($$0, $$2, (v0) -> {
            return v0.isAlive();
        });
        if (!$$3.isEmpty()) {
            throw assertionException(((Entity) $$3.getFirst()).blockPosition(), "test.error.unexpected_entity", $$0.getDescription());
        }
    }

    public void assertEntityTouching(EntityType<?> $$0, double $$1, double $$2, double $$3) {
        Vec3 $$4 = new Vec3($$1, $$2, $$3);
        Vec3 $$5 = absoluteVec($$4);
        Predicate<? super Entity> $$6 = $$12 -> {
            return $$12.getBoundingBox().intersects($$5, $$5);
        };
        if (!getLevel().hasEntities($$0, getBounds(), $$6)) {
            throw assertionException("test.error.expected_entity_touching", $$0.getDescription(), Double.valueOf($$5.x()), Double.valueOf($$5.y()), Double.valueOf($$5.z()), Double.valueOf($$1), Double.valueOf($$2), Double.valueOf($$3));
        }
    }

    public void assertEntityNotTouching(EntityType<?> $$0, double $$1, double $$2, double $$3) {
        Vec3 $$4 = new Vec3($$1, $$2, $$3);
        Vec3 $$5 = absoluteVec($$4);
        Predicate<? super Entity> $$6 = $$12 -> {
            return !$$12.getBoundingBox().intersects($$5, $$5);
        };
        if (!getLevel().hasEntities($$0, getBounds(), $$6)) {
            throw assertionException("test.error.expected_entity_not_touching", $$0.getDescription(), Double.valueOf($$5.x()), Double.valueOf($$5.y()), Double.valueOf($$5.z()), Double.valueOf($$1), Double.valueOf($$2), Double.valueOf($$3));
        }
    }

    public <E extends Entity, T> void assertEntityData(BlockPos $$0, EntityType<E> $$1, Predicate<E> predicate) {
        BlockPos $$3 = absolutePos($$0);
        List<E> $$4 = getLevel().getEntities($$1, new AABB($$3), (v0) -> {
            return v0.isAlive();
        });
        if ($$4.isEmpty()) {
            throw assertionException($$0, "test.error.expected_entity", $$1.getDescription());
        }
        for (E $$5 : $$4) {
            if (!predicate.test($$5)) {
                throw assertionException($$5.blockPosition(), "test.error.expected_entity_data_predicate", $$5.getName());
            }
        }
    }

    public <E extends Entity, T> void assertEntityData(BlockPos $$0, EntityType<E> $$1, Function<? super E, T> $$2, T $$3) {
        assertEntityData(new AABB($$0), $$1, $$2, $$3);
    }

    public <E extends Entity, T> void assertEntityData(AABB $$0, EntityType<E> $$1, Function<? super E, T> $$2, T $$3) {
        List<E> $$4 = getLevel().getEntities($$1, absoluteAABB($$0), (v0) -> {
            return v0.isAlive();
        });
        if ($$4.isEmpty()) {
            throw assertionException(BlockPos.containing($$0.getBottomCenter()), "test.error.expected_entity", $$1.getDescription());
        }
        Iterator<E> it = $$4.iterator();
        while (it.hasNext()) {
            T $$6 = $$2.apply(it.next());
            if (!Objects.equals($$6, $$3)) {
                throw assertionException(BlockPos.containing($$0.getBottomCenter()), "test.error.expected_entity_data", $$3, $$6);
            }
        }
    }

    public <E extends LivingEntity> void assertEntityIsHolding(BlockPos $$0, EntityType<E> $$1, Item $$2) {
        BlockPos $$3 = absolutePos($$0);
        List<E> $$4 = getLevel().getEntities($$1, new AABB($$3), (v0) -> {
            return v0.isAlive();
        });
        if ($$4.isEmpty()) {
            throw assertionException($$0, "test.error.expected_entity", $$1.getDescription());
        }
        for (E $$5 : $$4) {
            if ($$5.isHolding($$2)) {
                return;
            }
        }
        throw assertionException($$0, "test.error.expected_entity_holding", $$2.getName());
    }

    public <E extends Entity & InventoryCarrier> void assertEntityInventoryContains(BlockPos $$0, EntityType<E> $$1, Item $$2) {
        BlockPos $$3 = absolutePos($$0);
        List<E> $$4 = getLevel().getEntities($$1, new AABB($$3), $$02 -> {
            return ((Entity) $$02).isAlive();
        });
        if ($$4.isEmpty()) {
            throw assertionException($$0, "test.error.expected_entity", $$1.getDescription());
        }
        for (E $$5 : $$4) {
            if ($$5.getInventory().hasAnyMatching($$12 -> {
                return $$12.is($$2);
            })) {
                return;
            }
        }
        throw assertionException($$0, "test.error.expected_entity_having", $$2.getName());
    }

    public void assertContainerEmpty(BlockPos $$0) {
        BaseContainerBlockEntity $$1 = (BaseContainerBlockEntity) getBlockEntity($$0, BaseContainerBlockEntity.class);
        if (!$$1.isEmpty()) {
            throw assertionException($$0, "test.error.expected_empty_container", new Object[0]);
        }
    }

    public void assertContainerContainsSingle(BlockPos $$0, Item $$1) {
        BaseContainerBlockEntity $$2 = (BaseContainerBlockEntity) getBlockEntity($$0, BaseContainerBlockEntity.class);
        if ($$2.countItem($$1) != 1) {
            throw assertionException($$0, "test.error.expected_container_contents_single", $$1.getName());
        }
    }

    public void assertContainerContains(BlockPos $$0, Item $$1) {
        BaseContainerBlockEntity $$2 = (BaseContainerBlockEntity) getBlockEntity($$0, BaseContainerBlockEntity.class);
        if ($$2.countItem($$1) == 0) {
            throw assertionException($$0, "test.error.expected_container_contents", $$1.getName());
        }
    }

    public void assertSameBlockStates(BoundingBox $$0, BlockPos $$1) {
        BlockPos.betweenClosedStream($$0).forEach($$2 -> {
            BlockPos $$3 = $$1.offset($$2.getX() - $$0.minX(), $$2.getY() - $$0.minY(), $$2.getZ() - $$0.minZ());
            assertSameBlockState($$2, $$3);
        });
    }

    public void assertSameBlockState(BlockPos $$0, BlockPos $$1) {
        BlockState $$2 = getBlockState($$0);
        BlockState $$3 = getBlockState($$1);
        if ($$2 != $$3) {
            throw assertionException($$0, "test.error.state_not_equal", $$3, $$2);
        }
    }

    public void assertAtTickTimeContainerContains(long $$0, BlockPos $$1, Item $$2) {
        runAtTickTime($$0, () -> {
            assertContainerContainsSingle($$1, $$2);
        });
    }

    public void assertAtTickTimeContainerEmpty(long $$0, BlockPos $$1) {
        runAtTickTime($$0, () -> {
            assertContainerEmpty($$1);
        });
    }

    public <E extends Entity, T> void succeedWhenEntityData(BlockPos $$0, EntityType<E> $$1, Function<E, T> $$2, T $$3) {
        succeedWhen(() -> {
            assertEntityData($$0, $$1, (Function<? super E, Object>) $$2, $$3);
        });
    }

    public <E extends Entity> void assertEntityProperty(E $$0, Predicate<E> $$1, Component $$2) {
        if (!$$1.test($$0)) {
            throw assertionException($$0.blockPosition(), "test.error.entity_property", $$0.getName(), $$2);
        }
    }

    public <E extends Entity, T> void assertEntityProperty(E $$0, Function<E, T> $$1, T $$2, Component $$3) {
        T $$4 = $$1.apply($$0);
        if (!$$4.equals($$2)) {
            throw assertionException($$0.blockPosition(), "test.error.entity_property_details", $$0.getName(), $$3, $$4, $$2);
        }
    }

    public void assertLivingEntityHasMobEffect(LivingEntity $$0, Holder<MobEffect> $$1, int $$2) {
        MobEffectInstance $$3 = $$0.getEffect($$1);
        if ($$3 == null || $$3.getAmplifier() != $$2) {
            throw assertionException("test.error.expected_entity_effect", $$0.getName(), PotionContents.getPotionDescription($$1, $$2));
        }
    }

    public void succeedWhenEntityPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
        succeedWhenEntityPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void succeedWhenEntityPresent(EntityType<?> $$0, BlockPos $$1) {
        succeedWhen(() -> {
            assertEntityPresent((EntityType<?>) $$0, $$1);
        });
    }

    public void succeedWhenEntityNotPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
        succeedWhenEntityNotPresent($$0, new BlockPos($$1, $$2, $$3));
    }

    public void succeedWhenEntityNotPresent(EntityType<?> $$0, BlockPos $$1) {
        succeedWhen(() -> {
            assertEntityNotPresent((EntityType<?>) $$0, $$1);
        });
    }

    public void succeed() {
        this.testInfo.succeed();
    }

    private void ensureSingleFinalCheck() {
        if (this.finalCheckAdded) {
            throw new IllegalStateException("This test already has final clause");
        }
        this.finalCheckAdded = true;
    }

    public void succeedIf(Runnable $$0) {
        ensureSingleFinalCheck();
        this.testInfo.createSequence().thenWaitUntil(0L, $$0).thenSucceed();
    }

    public void succeedWhen(Runnable $$0) {
        ensureSingleFinalCheck();
        this.testInfo.createSequence().thenWaitUntil($$0).thenSucceed();
    }

    public void succeedOnTickWhen(int $$0, Runnable $$1) {
        ensureSingleFinalCheck();
        this.testInfo.createSequence().thenWaitUntil($$0, $$1).thenSucceed();
    }

    public void runAtTickTime(long $$0, Runnable $$1) {
        this.testInfo.setRunAtTickTime($$0, $$1);
    }

    public void runAfterDelay(long $$0, Runnable $$1) {
        runAtTickTime(((long) this.testInfo.getTick()) + $$0, $$1);
    }

    public void randomTick(BlockPos $$0) {
        BlockPos $$1 = absolutePos($$0);
        ServerLevel $$2 = getLevel();
        $$2.getBlockState($$1).randomTick($$2, $$1, $$2.random);
    }

    public void tickBlock(BlockPos $$0) {
        BlockPos $$1 = absolutePos($$0);
        ServerLevel $$2 = getLevel();
        $$2.getBlockState($$1).tick($$2, $$1, $$2.random);
    }

    public void tickPrecipitation(BlockPos $$0) {
        BlockPos $$1 = absolutePos($$0);
        ServerLevel $$2 = getLevel();
        $$2.tickPrecipitation($$1);
    }

    public void tickPrecipitation() {
        AABB $$0 = getRelativeBounds();
        int $$1 = (int) Math.floor($$0.maxX);
        int $$2 = (int) Math.floor($$0.maxZ);
        int $$3 = (int) Math.floor($$0.maxY);
        for (int $$4 = (int) Math.floor($$0.minX); $$4 < $$1; $$4++) {
            for (int $$5 = (int) Math.floor($$0.minZ); $$5 < $$2; $$5++) {
                tickPrecipitation(new BlockPos($$4, $$3, $$5));
            }
        }
    }

    public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
        BlockPos $$3 = absolutePos(new BlockPos($$1, 0, $$2));
        return relativePos(getLevel().getHeightmapPos($$0, $$3)).getY();
    }

    public void fail(Component $$0, BlockPos $$1) {
        throw assertionException($$1, $$0);
    }

    public void fail(Component $$0, Entity $$1) {
        throw assertionException($$1.blockPosition(), $$0);
    }

    public void fail(Component $$0) {
        throw assertionException($$0);
    }

    public void fail(String $$0) {
        throw assertionException(Component.literal($$0));
    }

    public void failIf(Runnable $$0) {
        this.testInfo.createSequence().thenWaitUntil($$0).thenFail(() -> {
            return assertionException("test.error.fail", new Object[0]);
        });
    }

    public void failIfEver(Runnable $$0) {
        LongStream.range(this.testInfo.getTick(), this.testInfo.getTimeoutTicks()).forEach($$1 -> {
            GameTestInfo gameTestInfo = this.testInfo;
            Objects.requireNonNull($$0);
            gameTestInfo.setRunAtTickTime($$1, $$0::run);
        });
    }

    public GameTestSequence startSequence() {
        return this.testInfo.createSequence();
    }

    public BlockPos absolutePos(BlockPos $$0) {
        BlockPos $$1 = this.testInfo.getTestOrigin();
        BlockPos $$2 = $$1.offset((Vec3i) $$0);
        return StructureTemplate.transform($$2, Mirror.NONE, this.testInfo.getRotation(), $$1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public BlockPos relativePos(BlockPos $$0) throws MatchException {
        BlockPos $$1 = this.testInfo.getTestOrigin();
        Rotation $$2 = this.testInfo.getRotation().getRotated(Rotation.CLOCKWISE_180);
        BlockPos $$3 = StructureTemplate.transform($$0, Mirror.NONE, $$2, $$1);
        return $$3.subtract((Vec3i) $$1);
    }

    public AABB absoluteAABB(AABB $$0) {
        Vec3 $$1 = absoluteVec($$0.getMinPosition());
        Vec3 $$2 = absoluteVec($$0.getMaxPosition());
        return new AABB($$1, $$2);
    }

    public AABB relativeAABB(AABB $$0) {
        Vec3 $$1 = relativeVec($$0.getMinPosition());
        Vec3 $$2 = relativeVec($$0.getMaxPosition());
        return new AABB($$1, $$2);
    }

    public Vec3 absoluteVec(Vec3 $$0) {
        Vec3 $$1 = Vec3.atLowerCornerOf(this.testInfo.getTestOrigin());
        return StructureTemplate.transform($$1.add($$0), Mirror.NONE, this.testInfo.getRotation(), this.testInfo.getTestOrigin());
    }

    public Vec3 relativeVec(Vec3 $$0) {
        Vec3 $$1 = Vec3.atLowerCornerOf(this.testInfo.getTestOrigin());
        return StructureTemplate.transform($$0.subtract($$1), Mirror.NONE, this.testInfo.getRotation(), this.testInfo.getTestOrigin());
    }

    public Rotation getTestRotation() {
        return this.testInfo.getRotation();
    }

    public Direction getTestDirection() {
        return this.testInfo.getRotation().rotate(Direction.SOUTH);
    }

    public Direction getAbsoluteDirection(Direction $$0) {
        return getTestRotation().rotate($$0);
    }

    public void assertTrue(boolean $$0, Component $$1) {
        if (!$$0) {
            throw assertionException($$1);
        }
    }

    public void assertTrue(boolean $$0, String $$1) {
        assertTrue($$0, Component.literal($$1));
    }

    public <N> void assertValueEqual(N $$0, N $$1, String $$2) {
        assertValueEqual($$0, $$1, Component.literal($$2));
    }

    public <N> void assertValueEqual(N $$0, N $$1, Component $$2) {
        if (!$$0.equals($$1)) {
            throw assertionException("test.error.value_not_equal", $$2, $$0, $$1);
        }
    }

    public void assertFalse(boolean $$0, Component $$1) {
        assertTrue(!$$0, $$1);
    }

    public void assertFalse(boolean $$0, String $$1) {
        assertFalse($$0, Component.literal($$1));
    }

    public long getTick() {
        return this.testInfo.getTick();
    }

    public AABB getBounds() {
        return this.testInfo.getStructureBounds();
    }

    public AABB getRelativeBounds() {
        AABB $$0 = this.testInfo.getStructureBounds();
        Rotation $$1 = this.testInfo.getRotation();
        switch ($$1) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return new AABB(Density.SURFACE, Density.SURFACE, Density.SURFACE, $$0.getZsize(), $$0.getYsize(), $$0.getXsize());
            default:
                return new AABB(Density.SURFACE, Density.SURFACE, Density.SURFACE, $$0.getXsize(), $$0.getYsize(), $$0.getZsize());
        }
    }

    public void forEveryBlockInStructure(Consumer<BlockPos> $$0) {
        AABB $$1 = getRelativeBounds().contract(1.0d, 1.0d, 1.0d);
        BlockPos.MutableBlockPos.betweenClosedStream($$1).forEach($$0);
    }

    public void onEachTick(Runnable $$0) {
        LongStream.range(this.testInfo.getTick(), this.testInfo.getTimeoutTicks()).forEach($$1 -> {
            GameTestInfo gameTestInfo = this.testInfo;
            Objects.requireNonNull($$0);
            gameTestInfo.setRunAtTickTime($$1, $$0::run);
        });
    }

    public void placeAt(Player $$0, ItemStack $$1, BlockPos $$2, Direction $$3) {
        BlockPos $$4 = absolutePos($$2.relative($$3));
        BlockHitResult $$5 = new BlockHitResult(Vec3.atCenterOf($$4), $$3, $$4, false);
        UseOnContext $$6 = new UseOnContext($$0, InteractionHand.MAIN_HAND, $$5);
        $$1.useOn($$6);
    }

    public void setBiome(ResourceKey<Biome> $$0) {
        AABB $$1 = getBounds();
        BlockPos $$2 = BlockPos.containing($$1.minX, $$1.minY, $$1.minZ);
        BlockPos $$3 = BlockPos.containing($$1.maxX, $$1.maxY, $$1.maxZ);
        Either<Integer, CommandSyntaxException> $$4 = FillBiomeCommand.fill(getLevel(), $$2, $$3, getLevel().registryAccess().lookupOrThrow((ResourceKey) Registries.BIOME).getOrThrow($$0));
        if ($$4.right().isPresent()) {
            throw assertionException("test.error.set_biome", new Object[0]);
        }
    }
}
