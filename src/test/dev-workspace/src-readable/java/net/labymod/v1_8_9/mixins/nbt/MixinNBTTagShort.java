package net.labymod.v1_8_9.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagShort;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/nbt/MixinNBTTagShort.class */
@Mixin({dz.class})
public abstract class MixinNBTTagShort implements NBTTagShort {
    @Shadow
    public abstract short e();

    @Override // net.labymod.api.nbt.tags.NBTTagShort, net.labymod.api.nbt.NBTTag
    public Short value() {
        return Short.valueOf(e());
    }
}
