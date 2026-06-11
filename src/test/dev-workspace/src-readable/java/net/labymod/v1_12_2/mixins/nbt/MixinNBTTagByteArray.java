package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByteArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagByteArray.class */
@Mixin({fw.class})
public abstract class MixinNBTTagByteArray implements NBTTagByteArray {
    @Shadow
    public abstract byte[] c();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public byte[] value() {
        return c();
    }
}
