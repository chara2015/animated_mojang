package net.labymod.v1_21_10.mixins.core;

import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/core/MixinBlockPos.class */
@Mixin({ja.class})
@Implements({@Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockPos implements BlockPosition {
    @Intrinsic
    public int labyMod$getX() {
        return ((kf) this).a;
    }

    @Intrinsic
    public int labyMod$getY() {
        return ((kf) this).b;
    }

    @Intrinsic
    public int labyMod$getZ() {
        return ((kf) this).c;
    }
}
