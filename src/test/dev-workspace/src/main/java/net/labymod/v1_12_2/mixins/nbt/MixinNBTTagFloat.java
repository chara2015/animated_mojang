package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagFloat.class */
@Mixin({gb.class})
public abstract class MixinNBTTagFloat implements NBTTagFloat {
    @Shadow
    public abstract float i();

    @Override // net.labymod.api.nbt.tags.NBTTagFloat, net.labymod.api.nbt.NBTTag
    public Float value() {
        return Float.valueOf(i());
    }
}
