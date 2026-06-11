package net.labymod.v1_20_1.mixins.client.world.block;

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
import net.labymod.v1_20_1.client.world.block.properties.VersionedBlockPropertyHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/world/block/MixinBlockState.class */
@Mixin({dcb.class})
@Implements({@Interface(iface = BlockState.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockState extends a implements BlockState {
    private final BlockPropertyHolder labyMod$propertyHolder;

    @Unique
    private final a labyMod$blockPos;
    private final IntVector3 labyMod$position;

    @Shadow
    protected abstract dcb x();

    protected MixinBlockState(cpn $$0, ImmutableMap<dde<?>, Comparable<?>> $$1, MapCodec<dcb> $$2) {
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
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return 0;
        }
        eoo blockColors = enn.N().ax();
        return blockColors.a(x(), fewVar, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.s_().a(this.labyMod$blockPos, 0);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        few level = enn.N().s;
        if (level == null) {
            return 0;
        }
        return level.a((cmv) lightType.toMinecraft(), this.labyMod$blockPos);
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_1.mixins.client.world.block.MixinBlockState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/world/block/MixinBlockState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$block$RenderShape = new int[cvs.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cvs.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cvs.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$block$RenderShape[cvs.c.ordinal()] = 3;
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
        return x().r(enn.N().s, this.labyMod$blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        efb shape = x().j(enn.N().s, this.labyMod$blockPos);
        if (shape.b()) {
            return null;
        }
        eed bounds = shape.a();
        return new AxisAlignedBoundingBox(bounds.a, bounds.b, bounds.c, bounds.d, bounds.e, bounds.f);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        return !x().k(enn.N().s, this.labyMod$blockPos).b();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isFluid() {
        return !x().u().c();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isWater() {
        dxe fluidState = x().u();
        return fluidState.b(dxf.c) || fluidState.b(dxf.b);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isLava() {
        dxe fluidState = x().u();
        return fluidState.b(dxf.e) || fluidState.b(dxf.d);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        byo mcPlayer = (byo) player;
        return x().a(mcPlayer, mcPlayer.dI(), this.labyMod$blockPos);
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
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return false;
        }
        return a(fewVar, this.labyMod$blockPos, (bfn) jb.h.a((acq) entityType.getMinecraftLocation()));
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isRail() {
        return a(amw.N);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return false;
        }
        efb shape = k(fewVar, this.labyMod$blockPos);
        return shape.c(a.a) > 0.0d;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        few fewVar = enn.N().s;
        if (fewVar == null) {
            return false;
        }
        efb shape = k(fewVar, this.labyMod$blockPos);
        return cpn.a(shape, ha.b);
    }

    @Intrinsic
    public boolean labyMod$isRedstoneSource() {
        return x().m();
    }
}
