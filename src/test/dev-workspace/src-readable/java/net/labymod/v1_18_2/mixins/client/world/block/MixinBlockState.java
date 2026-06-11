package net.labymod.v1_18_2.mixins.client.world.block;

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
import net.labymod.v1_18_2.client.world.block.properties.VersionedBlockPropertyHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/world/block/MixinBlockState.class */
@Mixin({cov.class})
@Implements({@Interface(iface = BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends a implements BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final a labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract cov r();

    protected MixinBlockState(cdq $$0, ImmutableMap<cpy<?>, Comparable<?>> $$1, MapCodec<cov> $$2) {
        super($$0, $$1, $$2);
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
        return r().b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getTopColor() {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return 0;
        }
        dzj blockColors = dyr.D().ak();
        return blockColors.a(r(), emsVar, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.l_().b(this.labyMod$blockPos, 0);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        ems level = dyr.D().r;
        if (level == null) {
            return 0;
        }
        return level.a((cbe) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_18_2.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[cjf.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cjf.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cjf.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cjf.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.block.BlockState
    public RenderShape renderShape() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$RenderShape[r().h().ordinal()]) {
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
        return r().r(dyr.D().r, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        dqh shape = r().j(dyr.D().r, this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        dpj bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        return !r().k(dyr.D().r, this.labyMod$blockPos).b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isFluid() {
        return !r().o().c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isWater() {
        dix fluidState = r().o();
        return fluidState.b(diy.c) || fluidState.b(diy.b);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isLava() {
        dix fluidState = r().o();
        return fluidState.b(diy.e) || fluidState.b(diy.d);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        boj mcPlayer = (boj) player;
        return r().a(mcPlayer, mcPlayer.s, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public BlockPropertyHolder propertyHolder() {
        return this.labyMod$propertyHolder;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean requiresMinToolForDrops() {
        return r().s();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return false;
        }
        return a(emsVar, this.labyMod$blockPos, (axo) hb.W.a((yt) entityType.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isRail() {
        return a(ahy.G);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return false;
        }
        dqh shape = k(emsVar, this.labyMod$blockPos);
        return shape.c(a.a) > 0.0d;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        ems emsVar = dyr.D().r;
        if (emsVar == null) {
            return false;
        }
        dqh shape = k(emsVar, this.labyMod$blockPos);
        return cdq.a(shape, go.b);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return r().i();
    }
}
