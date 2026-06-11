package net.labymod.v1_21_8.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagInt;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/nbt/MixinIntTag.class */
@Mixin({un.class})
@Implements({@Interface(iface = NBTTagInt.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinIntTag implements NBTTagInt {
    @Shadow
    public abstract int shadow$n();

    @Intrinsic
    public Integer labyMod$value() {
        return Integer.valueOf(shadow$n());
    }
}
