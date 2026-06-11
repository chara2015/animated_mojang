package net.labymod.v1_12_2.mixins.util.math;

import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/util/math/MixinBlockPos.class */
@Mixin({et.class})
@Implements({@Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockPos implements BlockPosition {
    @Intrinsic
    public int labyMod$getX() {
        return ((fq) this).a;
    }

    @Intrinsic
    public int labyMod$getY() {
        return ((fq) this).b;
    }

    @Intrinsic
    public int labyMod$getZ() {
        return ((fq) this).c;
    }
}
