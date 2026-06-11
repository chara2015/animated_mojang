package net.minecraft.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SkullBlock.class */
public class SkullBlock extends AbstractSkullBlock {
    public static final MapCodec<SkullBlock> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Type.CODEC.fieldOf("kind").forGetter((v0) -> {
            return v0.getType();
        }), propertiesCodec()).apply($$0, SkullBlock::new);
    });
    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    private static final int ROTATIONS = MAX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    private static final VoxelShape SHAPE = Block.column(8.0d, Density.SURFACE, 8.0d);
    private static final VoxelShape SHAPE_PIGLIN = Block.column(10.0d, Density.SURFACE, 8.0d);

    @Override // net.minecraft.world.level.block.AbstractSkullBlock, net.minecraft.world.level.block.BaseEntityBlock, net.minecraft.world.level.block.Block, net.minecraft.world.level.block.state.BlockBehaviour
    public MapCodec<? extends SkullBlock> codec() {
        return CODEC;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SkullBlock$Type.class */
    public interface Type extends StringRepresentable {
        public static final Map<String, Type> TYPES = new Object2ObjectArrayMap();
        public static final Codec<Type> CODEC;

        static {
            Function function = (v0) -> {
                return v0.getSerializedName();
            };
            Map<String, Type> map = TYPES;
            Objects.requireNonNull(map);
            CODEC = Codec.stringResolver(function, (v1) -> {
                return r1.get(v1);
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/SkullBlock$Types.class */
    public enum Types implements Type {
        SKELETON("skeleton"),
        WITHER_SKELETON("wither_skeleton"),
        PLAYER("player"),
        ZOMBIE("zombie"),
        CREEPER("creeper"),
        PIGLIN("piglin"),
        DRAGON("dragon");

        private final String name;

        Types(String $$0) {
            this.name = $$0;
            TYPES.put($$0, this);
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    protected SkullBlock(Type $$0, BlockBehaviour.Properties $$1) {
        super($$0, $$1);
        registerDefaultState((BlockState) defaultBlockState().setValue(ROTATION, 0));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return getType() == Types.PIGLIN ? SHAPE_PIGLIN : SHAPE;
    }

    @Override // net.minecraft.world.level.block.AbstractSkullBlock, net.minecraft.world.level.block.Block
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return (BlockState) super.getStateForPlacement($$0).setValue(ROTATION, Integer.valueOf(RotationSegment.convertToSegment($$0.getRotation())));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState rotate(BlockState $$0, Rotation $$1) {
        return (BlockState) $$0.setValue(ROTATION, Integer.valueOf($$1.rotate(((Integer) $$0.getValue(ROTATION)).intValue(), ROTATIONS)));
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour
    protected BlockState mirror(BlockState $$0, Mirror $$1) {
        return (BlockState) $$0.setValue(ROTATION, Integer.valueOf($$1.mirror(((Integer) $$0.getValue(ROTATION)).intValue(), ROTATIONS)));
    }

    @Override // net.minecraft.world.level.block.AbstractSkullBlock, net.minecraft.world.level.block.Block
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        super.createBlockStateDefinition($$0);
        $$0.add(ROTATION);
    }
}
