package net.labymod.v1_21_3.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagIntArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/nbt/MixinIntArrayTag.class */
@Mixin({vb.class})
public abstract class MixinIntArrayTag implements NBTTagIntArray {
    @Shadow
    public abstract int[] g();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public int[] value() {
        return g();
    }
}
