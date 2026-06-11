package net.labymod.v1_20_6.mixins.client.world.block;

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
import net.labymod.v1_20_6.client.world.block.properties.VersionedBlockPropertyHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/world/block/MixinBlockState.class */
@Mixin({dse.class})
@Implements({@Interface(iface = BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends a implements BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final a labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract dse x();

    protected MixinBlockState(dfb $$0, Reference2ObjectArrayMap<dth<?>, Comparable<?>> $$1, MapCodec<dse> $$2) {
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
        return x().b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getTopColor() {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return 0;
        }
        fgk blockColors = ffh.Q().av();
        return blockColors.a(x(), fxxVar, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.y_().a(this.labyMod$blockPos, 0);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        fxx level = ffh.Q().r;
        if (level == null) {
            return 0;
        }
        return level.a((dcj) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_6.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[dlh.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[dlh.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[dlh.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[dlh.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.block.BlockState
    public RenderShape renderShape() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$block$RenderShape[x().l().ordinal()]) {
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
        return x().r(ffh.Q().r, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        ewm shape = x().j(ffh.Q().r, this.labyMod$blockPos);
        if (shape.c()) {
            return null;
        }
        evo bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        return !x().k(ffh.Q().r, this.labyMod$blockPos).c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isFluid() {
        return !x().u().c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isWater() {
        enx fluidState = x().u();
        return fluidState.b(eny.c) || fluidState.b(eny.b);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isLava() {
        enx fluidState = x().u();
        return fluidState.b(eny.e) || fluidState.b(eny.d);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        cmz mcPlayer = (cmz) player;
        return x().a(mcPlayer, mcPlayer.dP(), this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public BlockPropertyHolder propertyHolder() {
        return this.labyMod$propertyHolder;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean requiresMinToolForDrops() {
        return x().y();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isValidSpawnBlock(@NotNull ResourceLocation entityType) {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return false;
        }
        return a(fxxVar, this.labyMod$blockPos, (btc) lp.g.a((alf) entityType.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isRail() {
        return a(awp.N);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return false;
        }
        ewm shape = k(fxxVar, this.labyMod$blockPos);
        return shape.c(a.a) > 0.0d;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        fxx fxxVar = ffh.Q().r;
        if (fxxVar == null) {
            return false;
        }
        ewm shape = k(fxxVar, this.labyMod$blockPos);
        return dfb.a(shape, je.b);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return x().m();
    }
}
