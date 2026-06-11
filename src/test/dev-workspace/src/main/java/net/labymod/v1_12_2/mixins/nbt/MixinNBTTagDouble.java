package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagDouble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagDouble.class */
@Mixin({fz.class})
public abstract class MixinNBTTagDouble implements NBTTagDouble {
    @Shadow
    public abstract double h();

    @Override // net.labymod.api.nbt.tags.NBTTagDouble, net.labymod.api.nbt.NBTTag
    public Double value() {
        return Double.valueOf(h());
    }
}
