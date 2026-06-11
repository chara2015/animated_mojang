package net.labymod.v26_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagInt;
import net.minecraft.nbt.IntTag;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/nbt/MixinIntTag.class */
@Mixin({IntTag.class})
@Implements({@Interface(iface = NBTTagInt.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinIntTag implements NBTTagInt {
    @Shadow
    public abstract int shadow$value();

    @Intrinsic
    public Integer labyMod$value() {
        return Integer.valueOf(shadow$value());
    }
}
