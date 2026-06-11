package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLongArray;
import net.minecraft.nbt.LongArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinLongArrayTag.class */
@Mixin({LongArrayTag.class})
public abstract class MixinLongArrayTag implements NBTTagLongArray {
    @Shadow
    public abstract long[] getAsLongArray();

    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public long[] m72value() {
        return getAsLongArray();
    }
}

