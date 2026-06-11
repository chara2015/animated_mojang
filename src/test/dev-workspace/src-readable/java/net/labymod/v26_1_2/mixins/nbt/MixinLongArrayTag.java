package net.labymod.v26_1_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLongArray;
import net.minecraft.nbt.LongArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/nbt/MixinLongArrayTag.class */
@Mixin({LongArrayTag.class})
public abstract class MixinLongArrayTag implements NBTTagLongArray {
    @Shadow
    public abstract long[] getAsLongArray();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public long[] value() {
        return getAsLongArray();
    }
}
