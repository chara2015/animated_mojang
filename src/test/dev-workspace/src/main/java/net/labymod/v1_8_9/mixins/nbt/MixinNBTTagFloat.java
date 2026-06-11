package net.labymod.v1_8_9.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/nbt/MixinNBTTagFloat.class */
@Mixin({dr.class})
public abstract class MixinNBTTagFloat implements NBTTagFloat {
    @Shadow
    public abstract float h();

    @Override // net.labymod.api.nbt.tags.NBTTagFloat, net.labymod.api.nbt.NBTTag
    public Float value() {
        return Float.valueOf(h());
    }
}
