package net.labymod.v1_18_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByte;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/nbt/MixinByteTag.class */
@Mixin({oi.class})
public abstract class MixinByteTag implements NBTTagByte {
    @Shadow
    public abstract byte h();

    @Override // net.labymod.api.nbt.tags.NBTTagByte, net.labymod.api.nbt.NBTTag
    public Byte value() {
        return Byte.valueOf(h());
    }
}
