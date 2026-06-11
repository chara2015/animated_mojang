package net.labymod.v1_12_2.mixins.client.world.block.state;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/block/state/MixinBlockStateImpl_API.class */
@Mixin(targets = {"net/minecraft/block/state/BlockState$StateImplementation", "net/minecraft/block/state/BlockStateContainer$StateImplementation"})
public abstract class MixinBlockStateImpl_API implements BlockState {
    private final a blockPos = new a();
    private final IntVector3 position = new IntVector3();

    @Shadow
    public abstract aow u();

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        bsb level = bib.z().f;
        if (level == null) {
            return false;
        }
        return ((awt) this).q();
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        bhb box;
        bsb level = bib.z().f;
        if (level == null) {
            return false;
        }
        aow blockObj = u();
        return (blockObj == null || (box = ((awt) this).d(level, this.blockPos)) == null || box.e <= 0.0d) ? false : true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        aed entityPlayer = (aed) player;
        return ((awt) this).a(entityPlayer, entityPlayer.l, this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        bsb level = bib.z().f;
        return (level == null || ((awt) this).d(level, this.blockPos) == null) ? false : true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        return level.b((ana) lightType.toMinecraft(), this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        return level.j(this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public IntVector3 position() {
        return this.position;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public void setCoordinates(int x, int y, int z) {
        this.blockPos.c(x, y, z);
        this.position.set(x, y, z);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getTopColor() {
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        bik blockColors = bib.z().al();
        return blockColors.a((awt) this, level, this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    @Nullable
    public AxisAlignedBoundingBox bounds() {
        bhb boundingBox;
        bsb level = bib.z().f;
        if (level == null || (boundingBox = ((awt) this).e(level, this.blockPos)) == null) {
            return null;
        }
        return new AxisAlignedBoundingBox(boundingBox.a, boundingBox.b, boundingBox.c, boundingBox.d, boundingBox.e, boundingBox.f);
    }
}
