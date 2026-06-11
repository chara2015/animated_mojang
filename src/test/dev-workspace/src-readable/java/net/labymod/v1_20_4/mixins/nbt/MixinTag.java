package net.labymod.v1_20_4.mixins.nbt;

import java.io.DataOutput;
import net.labymod.api.nbt.NBTTag;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/nbt/MixinTag.class */
@Mixin({tk.class})
@Implements({@Interface(iface = NBTTag.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public interface MixinTag extends NBTTag {
    @Shadow
    void a(DataOutput dataOutput);

    @Intrinsic
    default void labyMod$write(@NotNull DataOutput output) {
        a(output);
    }
}
