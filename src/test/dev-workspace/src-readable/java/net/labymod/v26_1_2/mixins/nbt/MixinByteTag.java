package net.labymod.v26_1_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByte;
import net.minecraft.nbt.ByteTag;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/nbt/MixinByteTag.class */
@Mixin({ByteTag.class})
@Implements({@Interface(iface = NBTTagByte.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinByteTag implements NBTTagByte {
    @Shadow
    public abstract byte shadow$value();

    @Intrinsic
    public Byte labyMod$value() {
        return Byte.valueOf(shadow$value());
    }
}
