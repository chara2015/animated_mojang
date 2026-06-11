package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/VineBlock.class */
public class VineBlock extends Block {
    public static final MapCodec<VineBlock> CODEC = simpleCodec(VineBlock::new);
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = (Map) PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter($$0 -> {
        return $$0.getKey() != Direction.DOWN;
    }).collect(Util.toMap());
    private final Function<BlockState, VoxelShape> shapes;

    @Override // net.minecraft.world.level.block.Block, net.minecraft.world.level.block.state.BlockBehaviour
    public MapCodec<VineBlock> codec() {
        return CODEC;
    }

    public VineBlock(BlockBehaviour.Properties $$0) {
        super($$0);
        registerDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateDefinition.any()).setValue(UP, false)).setValue(NORTH, false)).setValue(EAST, false)).setValue(SOUTH, false)).setValue(WEST, false));
        this.shapes = makeShapes();
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> $$0 = Shapes.rotateAll(Block.boxZ(16.0d, Density.SURFACE, 1.0d));
        return getShapeForEachState($$1 -> {
            VoxelShape $$2 = Shapes.empty();
            for (Map.Entry<Direction, BooleanProperty> $$3 : PROPERTY_BY_DIRECTION.entrySet()) {
                if (((Boolean) $$1.getValue($$3.getValue())).booleanValue()) {
                    $$2 = Shapes.or($$2, (VoxelShape) $$0.get($$3.getKey()));
                }
            }
            return $$2.isEmpty() ? Shapes.block() : $$2;
        });
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return this.shapes.apply($$0);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected boolean propagatesSkylightDown(BlockState $$0) {
        return true;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
        return hasFaces(getUpdatedState($$0, $$1, $$2));
    }

    private boolean hasFaces(BlockState $$0) {
        return countFaces($$0) > 0;
    }

    private int countFaces(BlockState $$0) {
        int $$1 = 0;
        for (BooleanProperty $$2 : PROPERTY_BY_DIRECTION.values()) {
            if (((Boolean) $$0.getValue($$2)).booleanValue()) {
                $$1++;
            }
        }
        return $$1;
    }

    private boolean canSupportAtFace(BlockGetter $$0, BlockPos $$1, Direction $$2) {
        if ($$2 == Direction.DOWN) {
            return false;
        }
        BlockPos $$3 = $$1.relative($$2);
        if (isAcceptableNeighbour($$0, $$3, $$2)) {
            return true;
        }
        if ($$2.getAxis() != Direction.Axis.Y) {
            BooleanProperty $$4 = PROPERTY_BY_DIRECTION.get($$2);
            BlockState $$5 = $$0.getBlockState($$1.above());
            return $$5.is(this) && ((Boolean) $$5.getValue($$4)).booleanValue();
        }
        return false;
    }

    public static boolean isAcceptableNeighbour(BlockGetter $$0, BlockPos $$1, Direction $$2) {
        return MultifaceBlock.canAttachTo($$0, $$2, $$1, $$0.getBlockState($$1));
    }

    private BlockState getUpdatedState(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
        BlockPos $$3 = $$2.above();
        if (((Boolean) $$0.getValue(UP)).booleanValue()) {
            $$0 = (BlockState) $$0.setValue(UP, Boolean.valueOf(isAcceptableNeighbour($$1, $$3, Direction.DOWN)));
        }
        BlockState $$4 = null;
        for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            BooleanProperty $$6 = getPropertyForFace($$5);
            if (((Boolean) $$0.getValue($$6)).booleanValue()) {
                boolean $$7 = canSupportAtFace($$1, $$2, $$5);
                if (!$$7) {
                    if ($$4 == null) {
                        $$4 = $$1.getBlockState($$3);
                    }
                    $$7 = $$4.is(this) && ((Boolean) $$4.getValue($$6)).booleanValue();
                }
                $$0 = (BlockState) $$0.setValue($$6, Boolean.valueOf($$7));
            }
        }
        return $$0;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState updateShape(BlockState $$0, LevelReader $$1, ScheduledTickAccess $$2, BlockPos $$3, Direction $$4, BlockPos $$5, BlockState $$6, RandomSource $$7) {
        if ($$4 == Direction.DOWN) {
            return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
        }
        BlockState $$8 = getUpdatedState($$0, $$1, $$3);
        if (!hasFaces($$8)) {
            return Blocks.AIR.defaultBlockState();
        }
        return $$8;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
        if (!((Boolean) $$1.getGameRules().get(GameRules.SPREAD_VINES)).booleanValue() || $$3.nextInt(4) != 0) {
            return;
        }
        Direction $$4 = Direction.getRandom($$3);
        BlockPos $$5 = $$2.above();
        if ($$4.getAxis().isHorizontal() && !((Boolean) $$0.getValue(getPropertyForFace($$4))).booleanValue()) {
            if (!canSpread($$1, $$2)) {
                return;
            }
            BlockPos $$6 = $$2.relative($$4);
            BlockState $$7 = $$1.getBlockState($$6);
            if (!$$7.isAir()) {
                if (isAcceptableNeighbour($$1, $$6, $$4)) {
                    $$1.setBlock($$2, (BlockState) $$0.setValue(getPropertyForFace($$4), true), 2);
                    return;
                }
                return;
            }
            Direction $$8 = $$4.getClockWise();
            Direction $$9 = $$4.getCounterClockWise();
            boolean $$10 = ((Boolean) $$0.getValue(getPropertyForFace($$8))).booleanValue();
            boolean $$11 = ((Boolean) $$0.getValue(getPropertyForFace($$9))).booleanValue();
            BlockPos $$12 = $$6.relative($$8);
            BlockPos $$13 = $$6.relative($$9);
            if ($$10 && isAcceptableNeighbour($$1, $$12, $$8)) {
                $$1.setBlock($$6, (BlockState) defaultBlockState().setValue(getPropertyForFace($$8), true), 2);
                return;
            }
            if ($$11 && isAcceptableNeighbour($$1, $$13, $$9)) {
                $$1.setBlock($$6, (BlockState) defaultBlockState().setValue(getPropertyForFace($$9), true), 2);
                return;
            }
            Direction $$14 = $$4.getOpposite();
            if ($$10 && $$1.isEmptyBlock($$12) && isAcceptableNeighbour($$1, $$2.relative($$8), $$14)) {
                $$1.setBlock($$12, (BlockState) defaultBlockState().setValue(getPropertyForFace($$14), true), 2);
                return;
            }
            if ($$11 && $$1.isEmptyBlock($$13) && isAcceptableNeighbour($$1, $$2.relative($$9), $$14)) {
                $$1.setBlock($$13, (BlockState) defaultBlockState().setValue(getPropertyForFace($$14), true), 2);
                return;
            } else {
                if ($$3.nextFloat() < 0.05d && isAcceptableNeighbour($$1, $$6.above(), Direction.UP)) {
                    $$1.setBlock($$6, (BlockState) defaultBlockState().setValue(UP, true), 2);
                    return;
                }
                return;
            }
        }
        if ($$4 == Direction.UP && $$2.getY() < $$1.getMaxY()) {
            if (canSupportAtFace($$1, $$2, $$4)) {
                $$1.setBlock($$2, (BlockState) $$0.setValue(UP, true), 2);
                return;
            }
            if ($$1.isEmptyBlock($$5)) {
                if (!canSpread($$1, $$2)) {
                    return;
                }
                BlockState $$15 = $$0;
                for (Direction $$16 : Direction.Plane.HORIZONTAL) {
                    if ($$3.nextBoolean() || !isAcceptableNeighbour($$1, $$5.relative($$16), $$16)) {
                        $$15 = (BlockState) $$15.setValue(getPropertyForFace($$16), false);
                    }
                }
                if (hasHorizontalConnection($$15)) {
                    $$1.setBlock($$5, $$15, 2);
                    return;
                }
                return;
            }
        }
        if ($$2.getY() > $$1.getMinY()) {
            BlockPos $$17 = $$2.below();
            BlockState $$18 = $$1.getBlockState($$17);
            if ($$18.isAir() || $$18.is(this)) {
                BlockState $$19 = $$18.isAir() ? defaultBlockState() : $$18;
                BlockState $$20 = copyRandomFaces($$0, $$19, $$3);
                if ($$19 != $$20 && hasHorizontalConnection($$20)) {
                    $$1.setBlock($$17, $$20, 2);
                }
            }
        }
    }

    private BlockState copyRandomFaces(BlockState $$0, BlockState $$1, RandomSource $$2) {
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            if ($$2.nextBoolean()) {
                BooleanProperty $$4 = getPropertyForFace($$3);
                if (((Boolean) $$0.getValue($$4)).booleanValue()) {
                    $$1 = (BlockState) $$1.setValue($$4, true);
                }
            }
        }
        return $$1;
    }

    private boolean hasHorizontalConnection(BlockState $$0) {
        return ((Boolean) $$0.getValue(NORTH)).booleanValue() || ((Boolean) $$0.getValue(EAST)).booleanValue() || ((Boolean) $$0.getValue(SOUTH)).booleanValue() || ((Boolean) $$0.getValue(WEST)).booleanValue();
    }

    private boolean canSpread(BlockGetter $$0, BlockPos $$1) {
        Iterable<BlockPos> $$3 = BlockPos.betweenClosed($$1.getX() - 4, $$1.getY() - 1, $$1.getZ() - 4, $$1.getX() + 4, $$1.getY() + 1, $$1.getZ() + 4);
        int $$4 = 5;
        for (BlockPos $$5 : $$3) {
            if ($$0.getBlockState($$5).is(this)) {
                $$4--;
                if ($$4 <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
        BlockState $$2 = $$1.getLevel().getBlockState($$1.getClickedPos());
        if ($$2.is(this)) {
            return countFaces($$2) < PROPERTY_BY_DIRECTION.size();
        }
        return super.canBeReplaced($$0, $$1);
    }

    @Override // net.minecraft.world.level.block.Block
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
        boolean $$2 = $$1.is(this);
        BlockState $$3 = $$2 ? $$1 : defaultBlockState();
        for (Direction $$4 : $$0.getNearestLookingDirections()) {
            if ($$4 != Direction.DOWN) {
                BooleanProperty $$5 = getPropertyForFace($$4);
                boolean $$6 = $$2 && ((Boolean) $$1.getValue($$5)).booleanValue();
                if (!$$6 && canSupportAtFace($$0.getLevel(), $$0.getClickedPos(), $$4)) {
                    return (BlockState) $$3.setValue($$5, true);
                }
            }
        }
        if ($$2) {
            return $$3;
        }
        return null;
    }

    @Override // net.minecraft.world.level.block.Block
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(UP, NORTH, EAST, SOUTH, WEST);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState rotate(BlockState $$0, Rotation $$1) {
        switch ($$1) {
            case CLOCKWISE_180:
                return (BlockState) ((BlockState) ((BlockState) ((BlockState) $$0.setValue(NORTH, (Boolean) $$0.getValue(SOUTH))).setValue(EAST, (Boolean) $$0.getValue(WEST))).setValue(SOUTH, (Boolean) $$0.getValue(NORTH))).setValue(WEST, (Boolean) $$0.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return (BlockState) ((BlockState) ((BlockState) ((BlockState) $$0.setValue(NORTH, (Boolean) $$0.getValue(EAST))).setValue(EAST, (Boolean) $$0.getValue(SOUTH))).setValue(SOUTH, (Boolean) $$0.getValue(WEST))).setValue(WEST, (Boolean) $$0.getValue(NORTH));
            case CLOCKWISE_90:
                return (BlockState) ((BlockState) ((BlockState) ((BlockState) $$0.setValue(NORTH, (Boolean) $$0.getValue(WEST))).setValue(EAST, (Boolean) $$0.getValue(NORTH))).setValue(SOUTH, (Boolean) $$0.getValue(EAST))).setValue(WEST, (Boolean) $$0.getValue(SOUTH));
            default:
                return $$0;
        }
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState mirror(BlockState $$0, Mirror $$1) {
        switch ($$1) {
            case LEFT_RIGHT:
                return (BlockState) ((BlockState) $$0.setValue(NORTH, (Boolean) $$0.getValue(SOUTH))).setValue(SOUTH, (Boolean) $$0.getValue(NORTH));
            case FRONT_BACK:
                return (BlockState) ((BlockState) $$0.setValue(EAST, (Boolean) $$0.getValue(WEST))).setValue(WEST, (Boolean) $$0.getValue(EAST));
            default:
                return super.mirror($$0, $$1);
        }
    }

    public static BooleanProperty getPropertyForFace(Direction $$0) {
        return PROPERTY_BY_DIRECTION.get($$0);
    }
}
