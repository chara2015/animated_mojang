package net.labymod.v26_2_snapshot_8.mixins.core;

import net.labymod.api.client.world.block.BlockPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/core/MixinBlockPos.class */
@Mixin({BlockPos.class})
@Implements({@Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockPos implements BlockPosition {
    @Intrinsic
    public int labyMod$getX() {
        return ((Vec3i) this).x;
    }

    @Intrinsic
    public int labyMod$getY() {
        return ((Vec3i) this).y;
    }

    @Intrinsic
    public int labyMod$getZ() {
        return ((Vec3i) this).z;
    }
}
