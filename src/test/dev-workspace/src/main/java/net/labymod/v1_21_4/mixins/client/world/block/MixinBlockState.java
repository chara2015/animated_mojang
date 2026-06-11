package net.labymod.v1_21_4.mixins.client.world.block;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.block.properties.BlockPropertyHolder;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_4.client.world.block.properties.VersionedBlockPropertyHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/world/block/MixinBlockState.class */
@Mixin({dwy.class})
@Implements({@Interface(iface = BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends a implements BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final a labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract dwy B();

    protected MixinBlockState(djn $$0, Reference2ObjectArrayMap<dya<?>, Comparable<?>> $$1, MapCodec<dwy> $$2) {
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
        return B().b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getTopColor() {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return 0;
        }
        fmm blockColors = flk.Q().aw();
        return blockColors.a(B(), ggaVar, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.C_().a(this.labyMod$blockPos, 0);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        gga level = flk.Q().s;
        if (level == null) {
            return 0;
        }
        return level.a((dgs) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[dpy.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[dpy.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[dpy.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.block.BlockState
    public RenderShape renderShape() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$RenderShape[B().o().ordinal()]) {
            case 1:
                return RenderShape.INVISIBLE;
            case 2:
                return RenderShape.MODEL;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isCollisionShapeSolid() {
        return B().m(flk.Q().s, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        fbv shape = B().f(flk.Q().s, this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        faw bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        return !B().g(flk.Q().s, this.labyMod$blockPos).c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isFluid() {
        return !B().y().c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isWater() {
        eta fluidState = B().y();
        return fluidState.b(etb.c) || fluidState.b(etb.b);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isLava() {
        eta fluidState = B().y();
        return fluidState.b(etb.e) || fluidState.b(etb.d);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        coy mcPlayer = (coy) player;
        return B().a(mcPlayer, mcPlayer.dV(), this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public BlockPropertyHolder propertyHolder() {
        return this.labyMod$propertyHolder;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean requiresMinToolForDrops() {
        return B().C();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return false;
        }
        return a(ggaVar, this.labyMod$blockPos, (but) ((c) mb.f.c((akv) entityType.getMinecraftLocation()).orElseThrow()).a());
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isRail() {
        return a(awp.P);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return false;
        }
        fbv shape = g(ggaVar, this.labyMod$blockPos);
        return shape.c(a.a) > 0.0d;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        gga ggaVar = flk.Q().s;
        if (ggaVar == null) {
            return false;
        }
        fbv shape = g(ggaVar, this.labyMod$blockPos);
        return djn.a(shape, jn.b);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return B().p();
    }
}
