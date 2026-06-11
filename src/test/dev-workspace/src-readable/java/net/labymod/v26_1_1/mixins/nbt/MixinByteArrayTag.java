package net.labymod.v26_1_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByteArray;
import net.minecraft.nbt.ByteArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/nbt/MixinByteArrayTag.class */
@Mixin({ByteArrayTag.class})
public abstract class MixinByteArrayTag implements NBTTagByteArray {
    @Shadow
    public abstract byte[] getAsByteArray();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public byte[] value() {
        return getAsByteArray();
    }
}
