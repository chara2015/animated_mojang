package net.labymod.v1_21_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByteArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/nbt/MixinByteArrayTag.class */
@Mixin({ty.class})
public abstract class MixinByteArrayTag implements NBTTagByteArray {
    @Shadow
    public abstract byte[] e();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public byte[] value() {
        return e();
    }
}
