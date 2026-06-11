package net.labymod.v1_8_9.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagDouble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/nbt/MixinNBTTagDouble.class */
@Mixin({dp.class})
public abstract class MixinNBTTagDouble implements NBTTagDouble {
    @Shadow
    public abstract double g();

    @Override // net.labymod.api.nbt.tags.NBTTagDouble, net.labymod.api.nbt.NBTTag
    public Double value() {
        return Double.valueOf(g());
    }
}
