package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagIntArray;
import net.minecraft.nbt.IntArrayTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinIntArrayTag.class */
@Mixin({IntArrayTag.class})
public abstract class MixinIntArrayTag implements NBTTagIntArray {
    @Shadow
    public abstract int[] getAsIntArray();

    /* JADX INFO: renamed from: value, reason: merged with bridge method [inline-methods] */
    public int[] m70value() {
        return getAsIntArray();
    }
}

