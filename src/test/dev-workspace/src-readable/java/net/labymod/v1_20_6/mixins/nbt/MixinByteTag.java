package net.labymod.v1_20_6.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByte;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/nbt/MixinByteTag.class */
@Mixin({uq.class})
public abstract class MixinByteTag implements NBTTagByte {
    @Shadow
    public abstract byte i();

    @Override // net.labymod.api.nbt.tags.NBTTagByte, net.labymod.api.nbt.NBTTag
    public Byte value() {
        return Byte.valueOf(i());
    }
}
