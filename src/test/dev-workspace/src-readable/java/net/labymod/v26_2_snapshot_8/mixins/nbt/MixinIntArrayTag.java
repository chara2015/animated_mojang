package net.labymod.v26_2_snapshot_8.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagIntArray;
import net.minecraft.nbt.IntArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/nbt/MixinIntArrayTag.class */
@Mixin({IntArrayTag.class})
public abstract class MixinIntArrayTag implements NBTTagIntArray {
    @Shadow
    public abstract int[] getAsIntArray();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public int[] value() {
        return getAsIntArray();
    }
}
