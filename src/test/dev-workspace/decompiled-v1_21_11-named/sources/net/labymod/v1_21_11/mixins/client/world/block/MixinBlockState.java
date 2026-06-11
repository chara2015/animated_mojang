package net.labymod.v1_21_11.mixins.client.world.block;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_11.client.world.block.properties.VersionedBlockPropertyHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/block/MixinBlockState.class */
@Mixin({BlockState.class})
@Implements({@Interface(iface = net.labymod.api.client.world.block.BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends BlockBehaviour.BlockStateBase implements net.labymod.api.client.world.block.BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final BlockPos.MutableBlockPos labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract BlockState asState();

    protected MixinBlockState(Block $$0, Reference2ObjectArrayMap<Property<?>, Comparable<?>> $$1, MapCodec<BlockState> $$2) {
        super($$0, $$1, $$2);
        this.labyMod$propertyHolder = new VersionedBlockPropertyHolder(this);
        this.labyMod$blockPos = new BlockPos.MutableBlockPos(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }

    public void setCoordinates(int x, int y, int z) {
        this.labyMod$blockPos.set(x, y, z);
        this.labyMod$position.set(x, y, z);
    }

    public IntVector3 position() {
        return this.labyMod$position;
    }

    public net.labymod.api.client.world.block.Block block() {
        return asState().getBlock();
    }

    public int getTopColor() {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return 0;
        }
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        return blockColors.getColor(asState(), clientLevel, this.labyMod$blockPos);
    }

    public int getLightLevel() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0;
        }
        return level.getLightEngine().getRawBrightness(this.labyMod$blockPos, 0);
    }

    public int getLightLevel(LightType lightType) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return 0;
        }
        return level.getBrightness((LightLayer) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[RenderShape.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[RenderShape.INVISIBLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[RenderShape.MODEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public net.labymod.api.client.world.block.RenderShape renderShape() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$RenderShape[asState().getRenderShape().ordinal()]) {
            case 1:
                return net.labymod.api.client.world.block.RenderShape.INVISIBLE;
            case 2:
                return net.labymod.api.client.world.block.RenderShape.MODEL;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public boolean isCollisionShapeSolid() {
        return asState().isCollisionShapeFullBlock(Minecraft.getInstance().level, this.labyMod$blockPos);
    }

    @Nullable
    public AxisAlignedBoundingBox bounds() {
        VoxelShape shape = asState().getShape(Minecraft.getInstance().level, this.labyMod$blockPos);
        if (shape.isEmpty()) {
            return null;
        }
        AABB bounds = shape.bounds();
        return new AxisAlignedBoundingBox(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
    }

    public boolean hasCollision() {
        return !asState().getCollisionShape(Minecraft.getInstance().level, this.labyMod$blockPos).isEmpty();
    }

    public boolean isFluid() {
        return !asState().getFluidState().isEmpty();
    }

    public boolean isWater() {
        FluidState fluidState = asState().getFluidState();
        return fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER);
    }

    public boolean isLava() {
        FluidState fluidState = asState().getFluidState();
        return fluidState.is(Fluids.LAVA) || fluidState.is(Fluids.FLOWING_LAVA);
    }

    public float getHardness(Player player) {
        net.minecraft.world.entity.player.Player mcPlayer = (net.minecraft.world.entity.player.Player) player;
        return asState().getDestroyProgress(mcPlayer, mcPlayer.level(), this.labyMod$blockPos);
    }

    public BlockPropertyHolder propertyHolder() {
        return this.labyMod$propertyHolder;
    }

    public boolean requiresMinToolForDrops() {
        return asState().requiresCorrectToolForDrops();
    }

    public boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return false;
        }
        return isValidSpawn(clientLevel, this.labyMod$blockPos, (EntityType) ((Holder.Reference) BuiltInRegistries.ENTITY_TYPE.get((Identifier) entityType.getMinecraftLocation()).orElseThrow()).value());
    }

    public boolean isRail() {
        return is(BlockTags.RAILS);
    }

    public boolean hasInvisibleCollisionHeight() {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return false;
        }
        VoxelShape shape = getCollisionShape(clientLevel, this.labyMod$blockPos);
        return shape.max(Direction.Axis.X) > 0.0d;
    }

    public boolean isTopCollisionShapeFull() {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return false;
        }
        VoxelShape shape = getCollisionShape(clientLevel, this.labyMod$blockPos);
        return Block.isFaceFull(shape, Direction.UP);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return asState().isSignalSource();
    }
}
