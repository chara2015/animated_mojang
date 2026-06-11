package net.labymod.v1_12_2.mixins.client.world.block.state;

import com.google.common.collect.ImmutableMap;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_12_2.client.util.VersionedWailaUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/block/state/MixinBlockState_API.class */
@Mixin({awt.class})
public interface MixinBlockState_API extends BlockState, BlockPropertyHolder {
    @Shadow
    aow u();

    @Shadow
    ImmutableMap<axj<?>, Comparable<?>> t();

    @Shadow
    <T extends Comparable<T>> T c(axj<T> axjVar);

    @Override // net.labymod.api.client.world.block.BlockState
    default Block block() {
        return (Block) CastUtil.cast(u());
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.mixins.client.world.block.state.MixinBlockState_API$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/block/state/MixinBlockState_API$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$EnumBlockRenderType = new int[atj.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumBlockRenderType[atj.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumBlockRenderType[atj.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumBlockRenderType[atj.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default RenderShape renderShape() {
        atj renderType = ((awt) this).i();
        switch (AnonymousClass1.$SwitchMap$net$minecraft$util$EnumBlockRenderType[renderType.ordinal()]) {
            case 1:
            case 2:
                return RenderShape.INVISIBLE;
            case 3:
                return RenderShape.ENTITY_BLOCK_ANIMATED;
            default:
                return RenderShape.MODEL;
        }
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isCollisionShapeSolid() {
        return ((awt) this).g();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isFluid() {
        return ((awt) this).a().d();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isWater() {
        return isFluid() && ((awt) this).a() == bcz.h;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isLava() {
        return isFluid() && ((awt) this).a() == bcz.i;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default BlockPropertyHolder propertyHolder() {
        return this;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean requiresMinToolForDrops() {
        return VersionedWailaUtil.requiresMinToolForDrops(u());
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        return true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isRail() {
        return u() instanceof aos;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    default boolean isRedstoneSource() {
        aow block = u();
        return (block instanceof ath) || (block instanceof art) || (block instanceof apd) || (block instanceof atk) || (block instanceof atf) || (block instanceof app);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    default boolean hasProperty(BlockProperty property) {
        return t().containsKey((axj) property);
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
        axj<T> axjVar = (axj) CastUtil.cast(blockProperty);
        if (!t().containsKey(axjVar)) {
            return null;
        }
        Enum r0 = (T) c(axjVar);
        if (r0 instanceof Enum) {
            return (T) CastUtil.cast(Laby.references().enumMapperRegistry().from(r0));
        }
        return r0;
    }
}
