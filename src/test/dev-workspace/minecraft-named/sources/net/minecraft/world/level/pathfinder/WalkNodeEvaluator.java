package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/pathfinder/WalkNodeEvaluator.class */
public class WalkNodeEvaluator extends NodeEvaluator {
    public static final double SPACE_BETWEEN_WALL_POSTS = 0.5d;
    private static final double DEFAULT_MOB_JUMP_HEIGHT = 1.125d;
    private final Long2ObjectMap<PathType> pathTypesByPosCacheByMob = new Long2ObjectOpenHashMap();
    private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap();
    private final Node[] reusableNeighbors = new Node[Direction.Plane.HORIZONTAL.length()];

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public void prepare(PathNavigationRegion $$0, Mob $$1) {
        super.prepare($$0, $$1);
        $$1.onPathfindingStart();
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public void done() {
        this.mob.onPathfindingDone();
        this.pathTypesByPosCacheByMob.clear();
        this.collisionCache.clear();
        super.done();
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public Node getStart() {
        BlockPos.MutableBlockPos $$0 = new BlockPos.MutableBlockPos();
        int $$1 = this.mob.getBlockY();
        BlockState $$2 = this.currentContext.getBlockState($$0.set(this.mob.getX(), $$1, this.mob.getZ()));
        if (this.mob.canStandOnFluid($$2.getFluidState())) {
            while (this.mob.canStandOnFluid($$2.getFluidState())) {
                $$1++;
                $$2 = this.currentContext.getBlockState($$0.set(this.mob.getX(), $$1, this.mob.getZ()));
            }
            $$1--;
        } else if (canFloat() && this.mob.isInWater()) {
            while (true) {
                if (!$$2.is(Blocks.WATER) && $$2.getFluidState() != Fluids.WATER.getSource(false)) {
                    break;
                }
                $$1++;
                $$2 = this.currentContext.getBlockState($$0.set(this.mob.getX(), $$1, this.mob.getZ()));
            }
            $$1--;
        } else if (this.mob.onGround()) {
            $$1 = Mth.floor(this.mob.getY() + 0.5d);
        } else {
            $$0.set(this.mob.getX(), this.mob.getY() + 1.0d, this.mob.getZ());
            while ($$0.getY() > this.currentContext.level().getMinY()) {
                $$1 = $$0.getY();
                $$0.setY($$0.getY() - 1);
                BlockState $$3 = this.currentContext.getBlockState($$0);
                if (!$$3.isAir() && !$$3.isPathfindable(PathComputationType.LAND)) {
                    break;
                }
            }
        }
        BlockPos $$4 = this.mob.blockPosition();
        if (!canStartAt($$0.set($$4.getX(), $$1, $$4.getZ()))) {
            AABB $$5 = this.mob.getBoundingBox();
            if (canStartAt($$0.set($$5.minX, $$1, $$5.minZ)) || canStartAt($$0.set($$5.minX, $$1, $$5.maxZ)) || canStartAt($$0.set($$5.maxX, $$1, $$5.minZ)) || canStartAt($$0.set($$5.maxX, $$1, $$5.maxZ))) {
                return getStartNode($$0);
            }
        }
        return getStartNode(new BlockPos($$4.getX(), $$1, $$4.getZ()));
    }

    protected Node getStartNode(BlockPos $$0) {
        Node $$1 = getNode($$0);
        $$1.type = getCachedPathType($$1.x, $$1.y, $$1.z);
        $$1.costMalus = this.mob.getPathfindingMalus($$1.type);
        return $$1;
    }

    protected boolean canStartAt(BlockPos $$0) {
        PathType $$1 = getCachedPathType($$0.getX(), $$0.getY(), $$0.getZ());
        return $$1 != PathType.OPEN && this.mob.getPathfindingMalus($$1) >= 0.0f;
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public Target getTarget(double $$0, double $$1, double $$2) {
        return getTargetNodeAt($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public int getNeighbors(Node[] $$0, Node $$1) {
        int $$2 = 0;
        int $$3 = 0;
        PathType $$4 = getCachedPathType($$1.x, $$1.y + 1, $$1.z);
        PathType $$5 = getCachedPathType($$1.x, $$1.y, $$1.z);
        if (this.mob.getPathfindingMalus($$4) >= 0.0f && $$5 != PathType.STICKY_HONEY) {
            $$3 = Mth.floor(Math.max(1.0f, this.mob.maxUpStep()));
        }
        double $$6 = getFloorLevel(new BlockPos($$1.x, $$1.y, $$1.z));
        for (Direction $$7 : Direction.Plane.HORIZONTAL) {
            Node $$8 = findAcceptedNode($$1.x + $$7.getStepX(), $$1.y, $$1.z + $$7.getStepZ(), $$3, $$6, $$7, $$5);
            this.reusableNeighbors[$$7.get2DDataValue()] = $$8;
            if (isNeighborValid($$8, $$1)) {
                int i = $$2;
                $$2++;
                $$0[i] = $$8;
            }
        }
        for (Direction $$9 : Direction.Plane.HORIZONTAL) {
            Direction $$10 = $$9.getClockWise();
            if (isDiagonalValid($$1, this.reusableNeighbors[$$9.get2DDataValue()], this.reusableNeighbors[$$10.get2DDataValue()])) {
                Node $$11 = findAcceptedNode($$1.x + $$9.getStepX() + $$10.getStepX(), $$1.y, $$1.z + $$9.getStepZ() + $$10.getStepZ(), $$3, $$6, $$9, $$5);
                if (isDiagonalValid($$11)) {
                    int i2 = $$2;
                    $$2++;
                    $$0[i2] = $$11;
                }
            }
        }
        return $$2;
    }

    protected boolean isNeighborValid(Node $$0, Node $$1) {
        return ($$0 == null || $$0.closed || ($$0.costMalus < 0.0f && $$1.costMalus >= 0.0f)) ? false : true;
    }

    protected boolean isDiagonalValid(Node $$0, Node $$1, Node $$2) {
        if ($$2 == null || $$1 == null || $$2.y > $$0.y || $$1.y > $$0.y || $$1.type == PathType.WALKABLE_DOOR || $$2.type == PathType.WALKABLE_DOOR) {
            return false;
        }
        boolean $$3 = $$2.type == PathType.FENCE && $$1.type == PathType.FENCE && ((double) this.mob.getBbWidth()) < 0.5d;
        return ($$2.y < $$0.y || $$2.costMalus >= 0.0f || $$3) && ($$1.y < $$0.y || $$1.costMalus >= 0.0f || $$3);
    }

    protected boolean isDiagonalValid(Node $$0) {
        return ($$0 == null || $$0.closed || $$0.type == PathType.WALKABLE_DOOR || $$0.costMalus < 0.0f) ? false : true;
    }

    private static boolean doesBlockHavePartialCollision(PathType $$0) {
        return $$0 == PathType.FENCE || $$0 == PathType.DOOR_WOOD_CLOSED || $$0 == PathType.DOOR_IRON_CLOSED;
    }

    private boolean canReachWithoutCollision(Node $$0) {
        AABB $$1 = this.mob.getBoundingBox();
        Vec3 $$2 = new Vec3((((double) $$0.x) - this.mob.getX()) + ($$1.getXsize() / 2.0d), (((double) $$0.y) - this.mob.getY()) + ($$1.getYsize() / 2.0d), (((double) $$0.z) - this.mob.getZ()) + ($$1.getZsize() / 2.0d));
        int $$3 = Mth.ceil($$2.length() / $$1.getSize());
        Vec3 $$22 = $$2.scale(1.0f / $$3);
        for (int $$4 = 1; $$4 <= $$3; $$4++) {
            $$1 = $$1.move($$22);
            if (hasCollisions($$1)) {
                return false;
            }
        }
        return true;
    }

    protected double getFloorLevel(BlockPos $$0) {
        BlockGetter $$1 = this.currentContext.level();
        if ((canFloat() || isAmphibious()) && $$1.getFluidState($$0).is(FluidTags.WATER)) {
            return ((double) $$0.getY()) + 0.5d;
        }
        return getFloorLevel($$1, $$0);
    }

    public static double getFloorLevel(BlockGetter $$0, BlockPos $$1) {
        BlockPos $$2 = $$1.below();
        VoxelShape $$3 = $$0.getBlockState($$2).getCollisionShape($$0, $$2);
        return ((double) $$2.getY()) + ($$3.isEmpty() ? Density.SURFACE : $$3.max(Direction.Axis.Y));
    }

    protected boolean isAmphibious() {
        return false;
    }

    protected Node findAcceptedNode(int $$0, int $$1, int $$2, int $$3, double $$4, Direction $$5, PathType $$6) {
        Node $$7 = null;
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        double $$9 = getFloorLevel($$8.set($$0, $$1, $$2));
        if ($$9 - $$4 > getMobJumpHeight()) {
            return null;
        }
        PathType $$10 = getCachedPathType($$0, $$1, $$2);
        float $$11 = this.mob.getPathfindingMalus($$10);
        if ($$11 >= 0.0f) {
            $$7 = getNodeAndUpdateCostToMax($$0, $$1, $$2, $$10, $$11);
        }
        if (doesBlockHavePartialCollision($$6) && $$7 != null && $$7.costMalus >= 0.0f && !canReachWithoutCollision($$7)) {
            $$7 = null;
        }
        if ($$10 == PathType.WALKABLE || (isAmphibious() && $$10 == PathType.WATER)) {
            return $$7;
        }
        if (($$7 == null || $$7.costMalus < 0.0f) && $$3 > 0 && (($$10 != PathType.FENCE || canWalkOverFences()) && $$10 != PathType.UNPASSABLE_RAIL && $$10 != PathType.TRAPDOOR && $$10 != PathType.POWDER_SNOW)) {
            $$7 = tryJumpOn($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$8);
        } else if (!isAmphibious() && $$10 == PathType.WATER && !canFloat()) {
            $$7 = tryFindFirstNonWaterBelow($$0, $$1, $$2, $$7);
        } else if ($$10 == PathType.OPEN) {
            $$7 = tryFindFirstGroundNodeBelow($$0, $$1, $$2);
        } else if (doesBlockHavePartialCollision($$10) && $$7 == null) {
            $$7 = getClosedNode($$0, $$1, $$2, $$10);
        }
        return $$7;
    }

    private double getMobJumpHeight() {
        return Math.max(DEFAULT_MOB_JUMP_HEIGHT, this.mob.maxUpStep());
    }

    private Node getNodeAndUpdateCostToMax(int $$0, int $$1, int $$2, PathType $$3, float $$4) {
        Node $$5 = getNode($$0, $$1, $$2);
        $$5.type = $$3;
        $$5.costMalus = Math.max($$5.costMalus, $$4);
        return $$5;
    }

    private Node getBlockedNode(int $$0, int $$1, int $$2) {
        Node $$3 = getNode($$0, $$1, $$2);
        $$3.type = PathType.BLOCKED;
        $$3.costMalus = -1.0f;
        return $$3;
    }

    private Node getClosedNode(int $$0, int $$1, int $$2, PathType $$3) {
        Node $$4 = getNode($$0, $$1, $$2);
        $$4.closed = true;
        $$4.type = $$3;
        $$4.costMalus = $$3.getMalus();
        return $$4;
    }

    private Node tryJumpOn(int $$0, int $$1, int $$2, int $$3, double $$4, Direction $$5, PathType $$6, BlockPos.MutableBlockPos $$7) {
        Node $$8 = findAcceptedNode($$0, $$1 + 1, $$2, $$3 - 1, $$4, $$5, $$6);
        if ($$8 == null) {
            return null;
        }
        if (this.mob.getBbWidth() >= 1.0f) {
            return $$8;
        }
        if ($$8.type != PathType.OPEN && $$8.type != PathType.WALKABLE) {
            return $$8;
        }
        double $$9 = ((double) ($$0 - $$5.getStepX())) + 0.5d;
        double $$10 = ((double) ($$2 - $$5.getStepZ())) + 0.5d;
        double $$11 = ((double) this.mob.getBbWidth()) / 2.0d;
        AABB $$12 = new AABB($$9 - $$11, getFloorLevel($$7.set($$9, $$1 + 1, $$10)) + 0.001d, $$10 - $$11, $$9 + $$11, (((double) this.mob.getBbHeight()) + getFloorLevel($$7.set($$8.x, $$8.y, $$8.z))) - 0.002d, $$10 + $$11);
        if (hasCollisions($$12)) {
            return null;
        }
        return $$8;
    }

    private Node tryFindFirstNonWaterBelow(int $$0, int $$1, int $$2, Node $$3) {
        while (true) {
            $$1--;
            if ($$1 > this.mob.level().getMinY()) {
                PathType $$4 = getCachedPathType($$0, $$1, $$2);
                if ($$4 == PathType.WATER) {
                    $$3 = getNodeAndUpdateCostToMax($$0, $$1, $$2, $$4, this.mob.getPathfindingMalus($$4));
                } else {
                    return $$3;
                }
            } else {
                return $$3;
            }
        }
    }

    private Node tryFindFirstGroundNodeBelow(int $$0, int $$1, int $$2) {
        for (int $$3 = $$1 - 1; $$3 >= this.mob.level().getMinY(); $$3--) {
            if ($$1 - $$3 > this.mob.getMaxFallDistance()) {
                return getBlockedNode($$0, $$3, $$2);
            }
            PathType $$4 = getCachedPathType($$0, $$3, $$2);
            float $$5 = this.mob.getPathfindingMalus($$4);
            if ($$4 != PathType.OPEN) {
                if ($$5 >= 0.0f) {
                    return getNodeAndUpdateCostToMax($$0, $$3, $$2, $$4, $$5);
                }
                return getBlockedNode($$0, $$3, $$2);
            }
        }
        return getBlockedNode($$0, $$1, $$2);
    }

    private boolean hasCollisions(AABB $$0) {
        return this.collisionCache.computeIfAbsent($$0, $$1 -> {
            return !this.currentContext.level().noCollision(this.mob, $$0);
        });
    }

    protected PathType getCachedPathType(int $$0, int $$1, int $$2) {
        return (PathType) this.pathTypesByPosCacheByMob.computeIfAbsent(BlockPos.asLong($$0, $$1, $$2), $$3 -> {
            return getPathTypeOfMob(this.currentContext, $$0, $$1, $$2, this.mob);
        });
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public PathType getPathTypeOfMob(PathfindingContext $$0, int $$1, int $$2, int $$3, Mob $$4) {
        Set<PathType> $$5 = getPathTypeWithinMobBB($$0, $$1, $$2, $$3);
        if ($$5.contains(PathType.FENCE)) {
            return PathType.FENCE;
        }
        if ($$5.contains(PathType.UNPASSABLE_RAIL)) {
            return PathType.UNPASSABLE_RAIL;
        }
        PathType $$6 = PathType.BLOCKED;
        for (PathType $$7 : $$5) {
            if ($$4.getPathfindingMalus($$7) < 0.0f) {
                return $$7;
            }
            if ($$4.getPathfindingMalus($$7) >= $$4.getPathfindingMalus($$6)) {
                $$6 = $$7;
            }
        }
        if (this.entityWidth <= 1 && $$6 != PathType.OPEN && $$4.getPathfindingMalus($$6) == 0.0f && getPathType($$0, $$1, $$2, $$3) == PathType.OPEN) {
            return PathType.OPEN;
        }
        return $$6;
    }

    public Set<PathType> getPathTypeWithinMobBB(PathfindingContext $$0, int $$1, int $$2, int $$3) {
        EnumSet<PathType> $$4 = EnumSet.noneOf(PathType.class);
        for (int $$5 = 0; $$5 < this.entityWidth; $$5++) {
            for (int $$6 = 0; $$6 < this.entityHeight; $$6++) {
                for (int $$7 = 0; $$7 < this.entityDepth; $$7++) {
                    int $$8 = $$5 + $$1;
                    int $$9 = $$6 + $$2;
                    int $$10 = $$7 + $$3;
                    PathType $$11 = getPathType($$0, $$8, $$9, $$10);
                    BlockPos $$12 = this.mob.blockPosition();
                    boolean $$13 = canPassDoors();
                    if ($$11 == PathType.DOOR_WOOD_CLOSED && canOpenDoors() && $$13) {
                        $$11 = PathType.WALKABLE_DOOR;
                    }
                    if ($$11 == PathType.DOOR_OPEN && !$$13) {
                        $$11 = PathType.BLOCKED;
                    }
                    if ($$11 == PathType.RAIL && getPathType($$0, $$12.getX(), $$12.getY(), $$12.getZ()) != PathType.RAIL && getPathType($$0, $$12.getX(), $$12.getY() - 1, $$12.getZ()) != PathType.RAIL) {
                        $$11 = PathType.UNPASSABLE_RAIL;
                    }
                    $$4.add($$11);
                }
            }
        }
        return $$4;
    }

    @Override // net.minecraft.world.level.pathfinder.NodeEvaluator
    public PathType getPathType(PathfindingContext $$0, int $$1, int $$2, int $$3) {
        return getPathTypeStatic($$0, new BlockPos.MutableBlockPos($$1, $$2, $$3));
    }

    public static PathType getPathTypeStatic(Mob $$0, BlockPos $$1) {
        return getPathTypeStatic(new PathfindingContext($$0.level(), $$0), $$1.mutable());
    }

    public static PathType getPathTypeStatic(PathfindingContext $$0, BlockPos.MutableBlockPos $$1) {
        int $$2 = $$1.getX();
        int $$3 = $$1.getY();
        int $$4 = $$1.getZ();
        PathType $$5 = $$0.getPathTypeFromState($$2, $$3, $$4);
        if ($$5 != PathType.OPEN || $$3 < $$0.level().getMinY() + 1) {
            return $$5;
        }
        switch ($$0.getPathTypeFromState($$2, $$3 - 1, $$4)) {
            case OPEN:
            case WATER:
            case LAVA:
            case WALKABLE:
                return PathType.OPEN;
            case DAMAGE_FIRE:
                return PathType.DAMAGE_FIRE;
            case DAMAGE_OTHER:
                return PathType.DAMAGE_OTHER;
            case STICKY_HONEY:
                return PathType.STICKY_HONEY;
            case POWDER_SNOW:
                return PathType.DANGER_POWDER_SNOW;
            case DAMAGE_CAUTIOUS:
                return PathType.DAMAGE_CAUTIOUS;
            case TRAPDOOR:
                return PathType.DANGER_TRAPDOOR;
            default:
                return checkNeighbourBlocks($$0, $$2, $$3, $$4, PathType.WALKABLE);
        }
    }

    public static PathType checkNeighbourBlocks(PathfindingContext $$0, int $$1, int $$2, int $$3, PathType $$4) {
        for (int $$5 = -1; $$5 <= 1; $$5++) {
            for (int $$6 = -1; $$6 <= 1; $$6++) {
                for (int $$7 = -1; $$7 <= 1; $$7++) {
                    if ($$5 != 0 || $$7 != 0) {
                        PathType $$8 = $$0.getPathTypeFromState($$1 + $$5, $$2 + $$6, $$3 + $$7);
                        if ($$8 == PathType.DAMAGE_OTHER) {
                            return PathType.DANGER_OTHER;
                        }
                        if ($$8 == PathType.DAMAGE_FIRE || $$8 == PathType.LAVA) {
                            return PathType.DANGER_FIRE;
                        }
                        if ($$8 == PathType.WATER) {
                            return PathType.WATER_BORDER;
                        }
                        if ($$8 == PathType.DAMAGE_CAUTIOUS) {
                            return PathType.DAMAGE_CAUTIOUS;
                        }
                    }
                }
            }
        }
        return $$4;
    }

    protected static PathType getPathTypeFromState(BlockGetter $$0, BlockPos $$1) {
        BlockState $$2 = $$0.getBlockState($$1);
        Block $$3 = $$2.getBlock();
        if ($$2.isAir()) {
            return PathType.OPEN;
        }
        if ($$2.is(BlockTags.TRAPDOORS) || $$2.is(Blocks.LILY_PAD) || $$2.is(Blocks.BIG_DRIPLEAF)) {
            return PathType.TRAPDOOR;
        }
        if ($$2.is(Blocks.POWDER_SNOW)) {
            return PathType.POWDER_SNOW;
        }
        if ($$2.is(Blocks.CACTUS) || $$2.is(Blocks.SWEET_BERRY_BUSH)) {
            return PathType.DAMAGE_OTHER;
        }
        if ($$2.is(Blocks.HONEY_BLOCK)) {
            return PathType.STICKY_HONEY;
        }
        if ($$2.is(Blocks.COCOA)) {
            return PathType.COCOA;
        }
        if ($$2.is(Blocks.WITHER_ROSE) || $$2.is(Blocks.POINTED_DRIPSTONE)) {
            return PathType.DAMAGE_CAUTIOUS;
        }
        FluidState $$4 = $$2.getFluidState();
        if ($$4.is(FluidTags.LAVA)) {
            return PathType.LAVA;
        }
        if (isBurningBlock($$2)) {
            return PathType.DAMAGE_FIRE;
        }
        if ($$3 instanceof DoorBlock) {
            DoorBlock $$5 = (DoorBlock) $$3;
            if (((Boolean) $$2.getValue(DoorBlock.OPEN)).booleanValue()) {
                return PathType.DOOR_OPEN;
            }
            return $$5.type().canOpenByHand() ? PathType.DOOR_WOOD_CLOSED : PathType.DOOR_IRON_CLOSED;
        }
        if ($$3 instanceof BaseRailBlock) {
            return PathType.RAIL;
        }
        if ($$3 instanceof LeavesBlock) {
            return PathType.LEAVES;
        }
        if ($$2.is(BlockTags.FENCES) || $$2.is(BlockTags.WALLS) || (($$3 instanceof FenceGateBlock) && !((Boolean) $$2.getValue(FenceGateBlock.OPEN)).booleanValue())) {
            return PathType.FENCE;
        }
        if (!$$2.isPathfindable(PathComputationType.LAND)) {
            return PathType.BLOCKED;
        }
        if ($$4.is(FluidTags.WATER)) {
            return PathType.WATER;
        }
        return PathType.OPEN;
    }
}
