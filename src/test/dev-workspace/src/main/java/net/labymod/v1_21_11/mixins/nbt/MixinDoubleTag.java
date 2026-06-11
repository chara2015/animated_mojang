package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagDouble;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/nbt/MixinDoubleTag.class */
@Mixin({va.class})
@Implements({@Interface(iface = NBTTagDouble.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinDoubleTag implements NBTTagDouble {
    @Shadow
    public abstract double shadow$n();

    @Intrinsic
    public Double labyMod$value() {
        return Double.valueOf(shadow$n());
    }
}
