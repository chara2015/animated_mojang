package net.labymod.v1_19_4.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/nbt/MixinFloatTag.class */
@Mixin({rh.class})
public abstract class MixinFloatTag implements NBTTagFloat {
    @Shadow
    public abstract float k();

    @Override // net.labymod.api.nbt.tags.NBTTagFloat, net.labymod.api.nbt.NBTTag
    public Float value() {
        return Float.valueOf(k());
    }
}
