package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByte;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagByte.class */
@Mixin({fx.class})
public abstract class MixinNBTTagByte implements NBTTagByte {
    @Shadow
    public abstract byte g();

    @Override // net.labymod.api.nbt.tags.NBTTagByte, net.labymod.api.nbt.NBTTag
    public Byte value() {
        return Byte.valueOf(g());
    }
}
