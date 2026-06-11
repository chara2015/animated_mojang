package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagByteArray;
import net.minecraft.nbt.ByteArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinByteArrayTag.class */
@Mixin({ByteArrayTag.class})
public abstract class MixinByteArrayTag implements NBTTagByteArray {
    @Shadow
    public abstract byte[] getAsByteArray();

    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public byte[] m68value() {
        return getAsByteArray();
    }
}

