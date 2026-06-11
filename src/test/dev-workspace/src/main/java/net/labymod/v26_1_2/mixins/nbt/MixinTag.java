package net.labymod.v26_1_2.mixins.nbt;

import java.io.DataOutput;
import net.labymod.api.nbt.NBTTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/nbt/MixinTag.class */
@Mixin({Tag.class})
@Implements({@Interface(iface = NBTTag.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public interface MixinTag extends NBTTag {
    @Override // net.labymod.api.nbt.NBTTag
    @Shadow
    void write(DataOutput dataOutput);

    @Intrinsic
    default void labyMod$write(@NotNull DataOutput output) {
        write(output);
    }
}
