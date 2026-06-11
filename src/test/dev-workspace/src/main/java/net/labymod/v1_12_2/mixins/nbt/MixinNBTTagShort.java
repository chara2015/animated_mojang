package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagShort;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagShort.class */
@Mixin({gl.class})
public abstract class MixinNBTTagShort implements NBTTagShort {
    @Shadow
    public abstract short f();

    @Override // net.labymod.api.nbt.tags.NBTTagShort, net.labymod.api.nbt.NBTTag
    public Short value() {
        return Short.valueOf(f());
    }
}
