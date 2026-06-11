package net.labymod.v1_21_8.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLong;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/nbt/MixinLongTag.class */
@Mixin({uq.class})
@Implements({@Interface(iface = NBTTagLong.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinLongTag implements NBTTagLong {
    @Shadow
    public abstract long shadow$n();

    @Intrinsic
    public Long labyMod$value() {
        return Long.valueOf(shadow$n());
    }
}
