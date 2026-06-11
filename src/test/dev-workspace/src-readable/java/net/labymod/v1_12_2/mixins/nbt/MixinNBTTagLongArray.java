package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLongArray;
import net.labymod.v1_12_2.nbt.VersionedNBTTagLongArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagLongArray.class */
@Mixin(value = {VersionedNBTTagLongArray.class}, remap = false)
public abstract class MixinNBTTagLongArray implements NBTTagLongArray {
    @Shadow
    public abstract long[] getLongArray();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public long[] value() {
        return getLongArray();
    }
}
