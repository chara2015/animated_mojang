package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagFloat;
import net.minecraft.nbt.FloatTag;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinFloatTag.class */
@Mixin({FloatTag.class})
@Implements({@Interface(iface = NBTTagFloat.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinFloatTag implements NBTTagFloat {
    @Shadow
    public abstract float shadow$value();

    @Intrinsic
    public Float labyMod$value() {
        return Float.valueOf(shadow$value());
    }
}

