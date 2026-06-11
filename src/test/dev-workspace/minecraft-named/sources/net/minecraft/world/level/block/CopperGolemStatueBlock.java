package net.minecraft.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.IntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CopperGolemStatueBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/CopperGolemStatueBlock.class */
public class CopperGolemStatueBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<CopperGolemStatueBlock> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter((v0) -> {
            return v0.getWeatheringState();
        }), propertiesCodec()).apply($$0, CopperGolemStatueBlock::new);
    });
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Pose> POSE = BlockStateProperties.COPPER_GOLEM_POSE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.column(10.0d, Density.SURFACE, 14.0d);
    private final WeatheringCopper.WeatherState weatheringState;

    @Override // net.minecraft.world.level.block.BaseEntityBlock, net.minecraft.world.level.block.Block, net.minecraft.world.level.block.state.BlockBehaviour
    public MapCodec<? extends CopperGolemStatueBlock> codec() {
        return CODEC;
    }

    public CopperGolemStatueBlock(WeatheringCopper.WeatherState $$0, BlockBehaviour.Properties $$1) {
        super($$1);
        this.weatheringState = $$0;
        registerDefaultState((BlockState) ((BlockState) ((BlockState) defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(POSE, Pose.STANDING)).setValue(WATERLOGGED, false));
    }

    @Override // net.minecraft.world.level.block.Block
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        super.createBlockStateDefinition($$0);
        $$0.add(FACING, POSE, WATERLOGGED);
    }

    @Override // net.minecraft.world.level.block.Block
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
        return (BlockState) ((BlockState) defaultBlockState().setValue(FACING, $$0.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, Boolean.valueOf($$1.getType() == Fluids.WATER));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState rotate(BlockState $$0, Rotation $$1) {
        return (BlockState) $$0.setValue(FACING, $$1.rotate((Direction) $$0.getValue(FACING)));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState mirror(BlockState $$0, Mirror $$1) {
        return $$0.rotate($$1.getRotation((Direction) $$0.getValue(FACING)));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return SHAPE;
    }

    public WeatheringCopper.WeatherState getWeatheringState() {
        return this.weatheringState;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected InteractionResult useItemOn(ItemStack $$0, BlockState $$1, Level $$2, BlockPos $$3, Player $$4, InteractionHand $$5, BlockHitResult $$6) {
        if ($$0.is(ItemTags.AXES)) {
            return InteractionResult.PASS;
        }
        updatePose($$2, $$1, $$3, $$4);
        return InteractionResult.SUCCESS;
    }

    void updatePose(Level $$0, BlockState $$1, BlockPos $$2, Player $$3) {
        $$0.playSound(null, $$2, SoundEvents.COPPER_GOLEM_BECOME_STATUE, SoundSource.BLOCKS);
        $$0.setBlock($$2, (BlockState) $$1.setValue(POSE, ((Pose) $$1.getValue(POSE)).getNextPose()), 3);
        $$0.gameEvent($$3, GameEvent.BLOCK_CHANGE, $$2);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected boolean isPathfindable(BlockState $$0, PathComputationType $$1) {
        return $$1 == PathComputationType.WATER && $$0.getFluidState().is(FluidTags.WATER);
    }

    @Override // net.minecraft.world.level.block.EntityBlock
    public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
        return new CopperGolemStatueBlockEntity($$0, $$1);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    public boolean shouldChangedStateKeepBlockEntity(BlockState $$0) {
        return $$0.is(BlockTags.COPPER_GOLEM_STATUES);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected boolean hasAnalogOutputSignal(BlockState $$0) {
        return true;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2, Direction $$3) {
        return ((Pose) $$0.getValue(POSE)).ordinal() + 1;
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2, boolean $$3) {
        BlockEntity blockEntity = $$0.getBlockEntity($$1);
        if (blockEntity instanceof CopperGolemStatueBlockEntity) {
            CopperGolemStatueBlockEntity $$4 = (CopperGolemStatueBlockEntity) blockEntity;
            return $$4.getItem(asItem().getDefaultInstance(), (Pose) $$2.getValue(POSE));
        }
        return super.getCloneItemStack($$0, $$1, $$2, $$3);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected void affectNeighborsAfterRemoval(BlockState $$0, ServerLevel $$1, BlockPos $$2, boolean $$3) {
        $$1.updateNeighbourForOutputSignal($$2, $$0.getBlock());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/CopperGolemStatueBlock$Pose.class */
    public enum Pose implements StringRepresentable {
        STANDING("standing"),
        SITTING("sitting"),
        RUNNING("running"),
        STAR("star");

        public static final IntFunction<Pose> BY_ID = ByIdMap.continuous((v0) -> {
            return v0.ordinal();
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Pose> CODEC = StringRepresentable.fromEnum(Pose::values);
        private final String name;

        Pose(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        public Pose getNextPose() {
            return BY_ID.apply(ordinal() + 1);
        }
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected FluidState getFluidState(BlockState $$0) {
        if (((Boolean) $$0.getValue(WATERLOGGED)).booleanValue()) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState($$0);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState updateShape(BlockState $$0, LevelReader $$1, ScheduledTickAccess $$2, BlockPos $$3, Direction $$4, BlockPos $$5, BlockState $$6, RandomSource $$7) {
        if (((Boolean) $$0.getValue(WATERLOGGED)).booleanValue()) {
            $$2.scheduleTick($$3, Fluids.WATER, Fluids.WATER.getTickDelay($$1));
        }
        return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }
}
