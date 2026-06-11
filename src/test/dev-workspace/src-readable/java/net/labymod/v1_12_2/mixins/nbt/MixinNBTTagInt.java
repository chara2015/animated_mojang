package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagInt.class */
@Mixin({gd.class})
public abstract class MixinNBTTagInt implements NBTTagInt {
    @Shadow
    public abstract int e();

    @Override // net.labymod.api.nbt.tags.NBTTagInt, net.labymod.api.nbt.NBTTag
    public Integer value() {
        return Integer.valueOf(e());
    }
}
