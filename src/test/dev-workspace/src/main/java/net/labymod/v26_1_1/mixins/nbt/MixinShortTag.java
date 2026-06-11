package net.labymod.v26_1_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagShort;
import net.minecraft.nbt.ShortTag;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/nbt/MixinShortTag.class */
@Mixin({ShortTag.class})
@Implements({@Interface(iface = NBTTagShort.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinShortTag implements NBTTagShort {
    @Shadow
    public abstract short shadow$value();

    @Intrinsic
    public Short labyMod$value() {
        return Short.valueOf(shadow$value());
    }
}
