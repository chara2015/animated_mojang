package net.labymod.v1_21_8.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagString;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/nbt/MixinStringTag.class */
@Mixin({vg.class})
@Implements({@Interface(iface = NBTTagString.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinStringTag implements NBTTagString {
    @Shadow
    public abstract String shadow$k();

    @Intrinsic
    @Nullable
    public String labyMod$value() {
        return shadow$k();
    }
}
