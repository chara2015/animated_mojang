package net.labymod.v1_20_6.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/nbt/MixinIntTag.class */
@Mixin({ux.class})
public abstract class MixinIntTag implements NBTTagInt {
    @Shadow
    public abstract int g();

    @Override // net.labymod.api.nbt.tags.NBTTagInt, net.labymod.api.nbt.NBTTag
    public Integer value() {
        return Integer.valueOf(g());
    }
}
