package net.labymod.v1_21_5.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByte;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/nbt/MixinByteTag.class */
@Mixin({ty.class})
@Implements({@Interface(iface = NBTTagByte.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinByteTag implements NBTTagByte {
    @Shadow
    public abstract byte shadow$n();

    @Intrinsic
    public Byte labyMod$value() {
        return Byte.valueOf(shadow$n());
    }
}
