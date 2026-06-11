package net.labymod.v1_21_3.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/nbt/MixinLongTag.class */
@Mixin({vf.class})
public abstract class MixinLongTag implements NBTTagLong {
    @Shadow
    public abstract long f();

    @Override // net.labymod.api.nbt.tags.NBTTagLong, net.labymod.api.nbt.NBTTag
    public Long value() {
        return Long.valueOf(f());
    }
}
