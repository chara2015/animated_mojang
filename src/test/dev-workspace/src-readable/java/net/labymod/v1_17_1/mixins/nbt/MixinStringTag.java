package net.labymod.v1_17_1.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagString;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/nbt/MixinStringTag.class */
@Mixin({nq.class})
public abstract class MixinStringTag implements NBTTagString {
    @Shadow
    public abstract String d_();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.tags.NBTTagString, net.labymod.api.nbt.NBTTag
    @Nullable
    public String value() {
        return d_();
    }
}
