package net.labymod.v1_12_2.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagString;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/nbt/MixinNBTTagString.class */
@Mixin({gm.class})
public abstract class MixinNBTTagString implements NBTTagString {
    @Shadow
    public abstract String c_();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.tags.NBTTagString, net.labymod.api.nbt.NBTTag
    @Nullable
    public String value() {
        return c_();
    }
}
