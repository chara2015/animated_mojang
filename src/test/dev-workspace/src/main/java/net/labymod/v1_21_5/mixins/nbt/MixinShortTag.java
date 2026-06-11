package net.labymod.v1_21_5.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagShort;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/nbt/MixinShortTag.class */
@Mixin({ut.class})
@Implements({@Interface(iface = NBTTagShort.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinShortTag implements NBTTagShort {
    @Shadow
    public abstract short shadow$n();

    @Intrinsic
    public Short labyMod$value() {
        return Short.valueOf(shadow$n());
    }
}
