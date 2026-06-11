package net.labymod.v1_21_11.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLong;
import net.minecraft.nbt.LongTag;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/nbt/MixinLongTag.class */
@Mixin({LongTag.class})
@Implements({@Interface(iface = NBTTagLong.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinLongTag implements NBTTagLong {
    @Shadow
    public abstract long shadow$value();

    @Intrinsic
    public Long labyMod$value() {
        return Long.valueOf(shadow$value());
    }
}

