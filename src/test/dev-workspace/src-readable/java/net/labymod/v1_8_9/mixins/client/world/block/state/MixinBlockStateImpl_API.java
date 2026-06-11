package net.labymod.v1_8_9.mixins.client.world.block.state;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.util.math.vector.IntVector3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/block/state/MixinBlockStateImpl_API.class */
@Mixin(targets = {"net/minecraft/block/state/BlockState$StateImplementation", "net/minecraft/block/state/BlockStateContainer$StateImplementation"})
public abstract class MixinBlockStateImpl_API implements BlockState {
    private final a blockPos = new a();
    private final IntVector3 position = new IntVector3();

    @Shadow
    public abstract afh c();

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean isTopCollisionShapeFull() {
        bdb level = ave.A().f;
        if (level == null) {
            return false;
        }
        return adm.a(level, this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasInvisibleCollisionHeight() {
        afh blockObj;
        aug box;
        bdb level = ave.A().f;
        return (level == null || (blockObj = c()) == null || (box = blockObj.a(level, this.blockPos, (alz) this)) == null || box.e <= 0.0d) ? false : true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public float getHardness(Player player) {
        wn entityPlayer = (wn) player;
        return c().a(entityPlayer, entityPlayer.o, this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public boolean hasCollision() {
        bdb level = ave.A().f;
        return (level == null || c().a(level, this.blockPos, (alz) this) == null) ? false : true;
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel(LightType lightType) {
        bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        return level.b((ads) lightType.toMinecraft(), this.blockPos);
    }

    @Override // net.labymod.api.client.world.block.BlockState
    public int getLightLevel() {
        bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        return level.k(this.blockPos);
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
}
