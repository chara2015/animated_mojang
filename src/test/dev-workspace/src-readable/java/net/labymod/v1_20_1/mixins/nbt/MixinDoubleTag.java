package net.labymod.v1_20_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagDouble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/nbt/MixinDoubleTag.class */
@Mixin({qs.class})
public abstract class MixinDoubleTag implements NBTTagDouble {
    @Shadow
    public abstract double j();

    @Override // net.labymod.api.nbt.tags.NBTTagDouble, net.labymod.api.nbt.NBTTag
    public Double value() {
        return Double.valueOf(j());
    }
}
