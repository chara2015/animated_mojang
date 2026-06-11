package net.labymod.v1_16_5.mixins.client.world.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_16_5.client.world.block.properties.VersionedBlockPropertyHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/block/MixinBlockState.class */
@Mixin({ceh.class})
@Implements({@Interface(iface = BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends a implements BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final a labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract ceh p();

    protected MixinBlockState(buo lvt_1_1_, ImmutableMap<cfj<?>, Comparable<?>> lvt_2_1_, MapCodec<ceh> lvt_3_1_) {
        super(lvt_1_1_, lvt_2_1_, lvt_3_1_);
        this.labyMod$propertyHolder = new VersionedBlockPropertyHolder(this);
        this.labyMod$blockPos = new a(0, 0, 0);
        this.labyMod$position = new IntVector3();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public void setCoordinates(int x, int y, int z) {
        this.labyMod$blockPos.d(x, y, z);
        this.labyMod$position.set(x, y, z);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public IntVector3 position() {
        return this.labyMod$position;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public Block block() {
        return p().b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getTopColor() {
        dwt dwtVar = djz.C().r;
        if (dwtVar == null) {
            return 0;
        }
        dko blockColors = djz.C().al();
        return blockColors.a(p(), dwtVar, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        dwt level = djz.C().r;
        if (level == null) {
            return 0;
        }
        return level.e().b(this.labyMod$blockPos, 0);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        dwt level = djz.C().r;
        if (level == null) {
            return 0;
        }
        return level.a((bsf) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[bzh.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[bzh.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[bzh.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[bzh.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.block.BlockState
    public RenderShape renderShape() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$RenderShape[p().h().ordinal()]) {
            case 1:
                return RenderShape.INVISIBLE;
            case 2:
                return RenderShape.ENTITY_BLOCK_ANIMATED;
            case 3:
                return RenderShape.MODEL;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isCollisionShapeSolid() {
        return p().r(djz.C().r, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        ddh shape = p().j(djz.C().r, this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        dci bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        return !p().k(djz.C().r, this.labyMod$blockPos).b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isFluid() {
        return !p().m().c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isWater() {
        cux fluidState = p().m();
        return fluidState.a(aef.b);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isLava() {
        cux fluidState = p().m();
        return fluidState.a(aef.c);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        bfw mcPlayer = (bfw) player;
        return p().a(mcPlayer, mcPlayer.l, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public BlockPropertyHolder propertyHolder() {
        return this.labyMod$propertyHolder;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean requiresMinToolForDrops() {
        return p().q();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        dwt dwtVar = djz.C().r;
        if (dwtVar == null) {
            return false;
        }
        return a(dwtVar, this.labyMod$blockPos, (aqe) gm.S.a((vk) entityType.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isRail() {
        return a(aed.H);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        dwt dwtVar = djz.C().r;
        if (dwtVar == null) {
            return false;
        }
        ddh shape = k(dwtVar, this.labyMod$blockPos);
        return shape.c(a.a) > 0.0d;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        dwt dwtVar = djz.C().r;
        if (dwtVar == null) {
            return false;
        }
        ddh shape = k(dwtVar, this.labyMod$blockPos);
        return buo.a(shape, gc.b);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return p().i();
    }
}
