package net.labymod.v1_16_5.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagShort;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/nbt/MixinShortTag.class */
@Mixin({mr.class})
public abstract class MixinShortTag implements NBTTagShort {
    @Shadow
    public abstract short g();

    @Override // net.labymod.api.nbt.tags.NBTTagShort, net.labymod.api.nbt.NBTTag
    public Short value() {
        return Short.valueOf(g());
    }
}
