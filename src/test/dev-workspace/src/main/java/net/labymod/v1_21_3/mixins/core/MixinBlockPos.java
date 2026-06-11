package net.labymod.v1_21_3.mixins.core;

import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/core/MixinBlockPos.class */
@Mixin({jh.class})
@Implements({@Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockPos implements BlockPosition {
    @Intrinsic
    public int labyMod$getX() {
        return ((kl) this).a;
    }

    @Intrinsic
    public int labyMod$getY() {
        return ((kl) this).b;
    }

    @Intrinsic
    public int labyMod$getZ() {
        return ((kl) this).c;
    }
}
