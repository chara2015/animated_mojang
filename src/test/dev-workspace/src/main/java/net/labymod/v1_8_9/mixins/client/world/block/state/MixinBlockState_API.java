package net.labymod.v1_8_9.mixins.client.world.block.state;

import com.google.common.collect.ImmutableMap;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.v1_8_9.client.util.VersionedWailaUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/block/state/MixinBlockState_API.class */
@Mixin({alz.class})
public interface MixinBlockState_API extends BlockState, BlockPropertyHolder {
    @Shadow
    afh c();

    @Shadow
    ImmutableMap<amo<?>, Comparable<?>> b();

    @Shadow
    <T extends Comparable<T>> T b(amo<T> amoVar);

    @Override // net.labymod.api.client.world.block.BlockState
    default Block block() {
        return (Block) CastUtil.cast(c());
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default int getTopColor() {
        bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        arn color = c().g((alz) this);
        if (color == null) {
            return -1;
        }
        return color.L;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default RenderShape renderShape() {
        int renderType = c().b();
        switch (renderType) {
            case -1:
                return RenderShape.INVISIBLE;
            case 2:
                return RenderShape.ENTITY_BLOCK_ANIMATED;
            default:
                return RenderShape.MODEL;
        }
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isCollisionShapeSolid() {
        return c().d();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    default AxisAlignedBoundingBox bounds() {
        afh block = c();
        return new AxisAlignedBoundingBox(block.B(), block.D(), block.F(), block.C(), block.E(), block.G());
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isFluid() {
        return c() instanceof ahv;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isWater() {
        return isFluid() && c().t() == arm.h;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isLava() {
        return isFluid() && c().t() == arm.i;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default BlockPropertyHolder propertyHolder() {
        return this;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean requiresMinToolForDrops() {
        return VersionedWailaUtil.requiresMinToolForDrops(c());
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        return true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isRail() {
        return c() instanceof afe;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isRedstoneSource() {
        afh block = c();
        return (block instanceof ajd) || (block instanceof ahu) || (block instanceof afn) || (block instanceof ajf) || (block instanceof ajb) || (block instanceof afx);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    default boolean hasProperty(BlockProperty property) {
        return b().containsKey((amo) property);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    @Nullable
    default Integer getInt(BlockProperty property) {
        return (Integer) getPropertyValue(property);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    @Nullable
    default Boolean getBoolean(BlockProperty property) {
        return (Boolean) getPropertyValue(property);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    default <E extends Enum<E>> E getEnum(BlockProperty property) {
        return (E) getPropertyValue(property);
    }

    private default <T extends Comparable<T>> T getPropertyValue(BlockProperty blockProperty) {
        amo<T> amoVar = (amo) CastUtil.cast(blockProperty);
        if (!b().containsKey(amoVar)) {
            return null;
        }
        Enum r0 = (T) b(amoVar);
        if (r0 instanceof Enum) {
            return (T) CastUtil.cast(Laby.references().enumMapperRegistry().from(r0));
        }
        return r0;
    }
}
