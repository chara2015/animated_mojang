package net.minecraft.world.level.block;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SideChainPart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SideChainPartBlock.class */
public interface SideChainPartBlock {
    SideChainPart getSideChainPart(BlockState blockState);

    BlockState setSideChainPart(BlockState blockState, SideChainPart sideChainPart);

    Direction getFacing(BlockState blockState);

    boolean isConnectable(BlockState blockState);

    int getMaxChainLength();

    default List<BlockPos> getAllBlocksConnectedTo(LevelAccessor $$0, BlockPos $$1) {
        BlockState $$2 = $$0.getBlockState($$1);
        if (!isConnectable($$2)) {
            return List.of();
        }
        Neighbors $$3 = getNeighbors($$0, $$1, getFacing($$2));
        List<BlockPos> $$4 = new LinkedList<>();
        $$4.add($$1);
        Objects.requireNonNull($$3);
        IntFunction<Neighbor> intFunction = $$3::left;
        SideChainPart sideChainPart = SideChainPart.LEFT;
        Objects.requireNonNull($$4);
        addBlocksConnectingTowards(intFunction, sideChainPart, (v1) -> {
            r3.addFirst(v1);
        });
        Objects.requireNonNull($$3);
        IntFunction<Neighbor> intFunction2 = $$3::right;
        SideChainPart sideChainPart2 = SideChainPart.RIGHT;
        Objects.requireNonNull($$4);
        addBlocksConnectingTowards(intFunction2, sideChainPart2, (v1) -> {
            r3.addLast(v1);
        });
        return $$4;
    }

    private default void addBlocksConnectingTowards(IntFunction<Neighbor> $$0, SideChainPart $$1, Consumer<BlockPos> $$2) {
        for (int $$3 = 1; $$3 < getMaxChainLength(); $$3++) {
            Neighbor $$4 = $$0.apply($$3);
            if ($$4.connectsTowards($$1)) {
                $$2.accept($$4.pos());
            }
            if ($$4.isUnconnectableOrChainEnd()) {
                return;
            }
        }
    }

    default void updateNeighborsAfterPoweringDown(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
        Neighbors $$3 = getNeighbors($$0, $$1, getFacing($$2));
        $$3.left().disconnectFromRight();
        $$3.right().disconnectFromLeft();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    default void updateSelfAndNeighborsOnPoweringUp(LevelAccessor $$0, BlockPos $$1, BlockState $$2, BlockState $$3) throws MatchException {
        if (!isConnectable($$2) || isBeingUpdatedByNeighbor($$2, $$3)) {
            return;
        }
        Neighbors $$4 = getNeighbors($$0, $$1, getFacing($$2));
        SideChainPart $$5 = SideChainPart.UNCONNECTED;
        int $$6 = $$4.left().isConnectable() ? getAllBlocksConnectedTo($$0, $$4.left().pos()).size() : 0;
        int $$7 = $$4.right().isConnectable() ? getAllBlocksConnectedTo($$0, $$4.right().pos()).size() : 0;
        int $$8 = 1;
        if (canConnect($$6, 1)) {
            $$5 = $$5.whenConnectedToTheLeft();
            $$4.left().connectToTheRight();
            $$8 = 1 + $$6;
        }
        if (canConnect($$7, $$8)) {
            $$5 = $$5.whenConnectedToTheRight();
            $$4.right().connectToTheLeft();
        }
        setPart($$0, $$1, $$5);
    }

    private default boolean canConnect(int $$0, int $$1) {
        return $$0 > 0 && $$1 + $$0 <= getMaxChainLength();
    }

    private default boolean isBeingUpdatedByNeighbor(BlockState $$0, BlockState $$1) {
        boolean $$2 = getSideChainPart($$0).isConnected();
        boolean $$3 = isConnectable($$1) && getSideChainPart($$1).isConnected();
        return $$2 || $$3;
    }

    private default Neighbors getNeighbors(LevelAccessor $$0, BlockPos $$1, Direction $$2) {
        return new Neighbors(this, $$0, $$2, $$1, new HashMap());
    }

    default void setPart(LevelAccessor $$0, BlockPos $$1, SideChainPart $$2) {
        BlockState $$3 = $$0.getBlockState($$1);
        if (getSideChainPart($$3) != $$2) {
            $$0.setBlock($$1, setSideChainPart($$3, $$2), 3);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SideChainPartBlock$Neighbors.class */
    public static final class Neighbors extends Record {
        private final SideChainPartBlock block;
        private final LevelAccessor level;
        private final Direction facing;
        private final BlockPos center;
        private final Map<BlockPos, Neighbor> cache;

        public Neighbors(SideChainPartBlock $$0, LevelAccessor $$1, Direction $$2, BlockPos $$3, Map<BlockPos, Neighbor> $$4) {
            this.block = $$0;
            this.level = $$1;
            this.facing = $$2;
            this.center = $$3;
            this.cache = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Neighbors.class), Neighbors.class, "block;level;facing;center;cache", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->facing:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->center:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->cache:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Neighbors.class), Neighbors.class, "block;level;facing;center;cache", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->facing:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->center:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->cache:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Neighbors.class, Object.class), Neighbors.class, "block;level;facing;center;cache", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->facing:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->center:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$Neighbors;->cache:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SideChainPartBlock block() {
            return this.block;
        }

        public LevelAccessor level() {
            return this.level;
        }

        public Direction facing() {
            return this.facing;
        }

        public BlockPos center() {
            return this.center;
        }

        public Map<BlockPos, Neighbor> cache() {
            return this.cache;
        }

        private boolean isConnectableToThisBlock(BlockState $$0) {
            return this.block.isConnectable($$0) && this.block.getFacing($$0) == this.facing;
        }

        private Neighbor createNewNeighbor(BlockPos $$0) {
            BlockState $$1 = this.level.getBlockState($$0);
            SideChainPart $$2 = isConnectableToThisBlock($$1) ? this.block.getSideChainPart($$1) : null;
            return $$2 == null ? new EmptyNeighbor($$0) : new SideChainNeighbor(this.level, this.block, $$0, $$2);
        }

        private Neighbor getOrCreateNeighbor(Direction $$0, Integer $$1) {
            return this.cache.computeIfAbsent(this.center.relative($$0, $$1.intValue()), this::createNewNeighbor);
        }

        public Neighbor left(int $$0) {
            return getOrCreateNeighbor(this.facing.getClockWise(), Integer.valueOf($$0));
        }

        public Neighbor right(int $$0) {
            return getOrCreateNeighbor(this.facing.getCounterClockWise(), Integer.valueOf($$0));
        }

        public Neighbor left() {
            return left(1);
        }

        public Neighbor right() {
            return right(1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SideChainPartBlock$Neighbor.class */
    public interface Neighbor {
        BlockPos pos();

        boolean isConnectable();

        boolean isUnconnectableOrChainEnd();

        boolean connectsTowards(SideChainPart sideChainPart);

        default void connectToTheRight() {
        }

        default void connectToTheLeft() {
        }

        default void disconnectFromRight() {
        }

        default void disconnectFromLeft() {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SideChainPartBlock$EmptyNeighbor.class */
    public static final class EmptyNeighbor extends Record implements Neighbor {
        private final BlockPos pos;

        public EmptyNeighbor(BlockPos $$0) {
            this.pos = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EmptyNeighbor.class), EmptyNeighbor.class, "pos", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$EmptyNeighbor;->pos:Lnet/minecraft/core/BlockPos;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EmptyNeighbor.class), EmptyNeighbor.class, "pos", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$EmptyNeighbor;->pos:Lnet/minecraft/core/BlockPos;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EmptyNeighbor.class, Object.class), EmptyNeighbor.class, "pos", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$EmptyNeighbor;->pos:Lnet/minecraft/core/BlockPos;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public BlockPos pos() {
            return this.pos;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean isConnectable() {
            return false;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean isUnconnectableOrChainEnd() {
            return true;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean connectsTowards(SideChainPart $$0) {
            return false;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor.class */
    public static final class SideChainNeighbor extends Record implements Neighbor {
        private final LevelAccessor level;
        private final SideChainPartBlock block;
        private final BlockPos pos;
        private final SideChainPart part;

        public SideChainNeighbor(LevelAccessor $$0, SideChainPartBlock $$1, BlockPos $$2, SideChainPart $$3) {
            this.level = $$0;
            this.block = $$1;
            this.pos = $$2;
            this.part = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SideChainNeighbor.class), SideChainNeighbor.class, "level;block;pos;part", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->part:Lnet/minecraft/world/level/block/state/properties/SideChainPart;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SideChainNeighbor.class), SideChainNeighbor.class, "level;block;pos;part", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->part:Lnet/minecraft/world/level/block/state/properties/SideChainPart;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SideChainNeighbor.class, Object.class), SideChainNeighbor.class, "level;block;pos;part", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->level:Lnet/minecraft/world/level/LevelAccessor;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->block:Lnet/minecraft/world/level/block/SideChainPartBlock;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/block/SideChainPartBlock$SideChainNeighbor;->part:Lnet/minecraft/world/level/block/state/properties/SideChainPart;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LevelAccessor level() {
            return this.level;
        }

        public SideChainPartBlock block() {
            return this.block;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public BlockPos pos() {
            return this.pos;
        }

        public SideChainPart part() {
            return this.part;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean isConnectable() {
            return true;
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean isUnconnectableOrChainEnd() {
            return this.part.isChainEnd();
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public boolean connectsTowards(SideChainPart $$0) {
            return this.part.isConnectionTowards($$0);
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public void connectToTheRight() {
            this.block.setPart(this.level, this.pos, this.part.whenConnectedToTheRight());
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public void connectToTheLeft() {
            this.block.setPart(this.level, this.pos, this.part.whenConnectedToTheLeft());
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public void disconnectFromRight() {
            this.block.setPart(this.level, this.pos, this.part.whenDisconnectedFromTheRight());
        }

        @Override // net.minecraft.world.level.block.SideChainPartBlock.Neighbor
        public void disconnectFromLeft() {
            this.block.setPart(this.level, this.pos, this.part.whenDisconnectedFromTheLeft());
        }
    }
}
