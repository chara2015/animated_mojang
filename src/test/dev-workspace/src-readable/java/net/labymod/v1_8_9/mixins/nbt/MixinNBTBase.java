package net.labymod.v1_8_9.mixins.nbt;

import java.io.DataOutput;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.NBTTagType;
import net.labymod.v1_8_9.nbt.VersionedNBTTagLongArray;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/nbt/MixinNBTBase.class */
@Mixin({eb.class})
@Implements({@Interface(iface = NBTTag.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinNBTBase implements NBTTag {
    @Shadow
    public abstract void a(DataOutput dataOutput);

    @Inject(method = {"createNewByType"}, at = {@At("HEAD")}, cancellable = true)
    private static void createNewByType(byte id, CallbackInfoReturnable<eb> cir) {
        if (id == NBTTagType.LONG_ARRAY.getId()) {
            cir.setReturnValue(new VersionedNBTTagLongArray());
        }
    }

    @Intrinsic
    public void labyMod$write(@NotNull DataOutput output) {
        a(output);
    }
}
