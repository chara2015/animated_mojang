package net.labymod.v1_19_4.mixins.nbt;

import net.labymod.api.nbt.tags.NBTTagLongArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/nbt/MixinLongArrayTag.class */
@Mixin({rl.class})
public abstract class MixinLongArrayTag implements NBTTagLongArray {
    @Shadow
    public abstract long[] g();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.nbt.NBTTag
    public long[] value() {
        return g();
    }
}
