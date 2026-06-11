package net.labymod.v1_16_5.mixins.client.entity.decoration;

import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_16_5.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/entity/decoration/MixinItemFrame.class */
@Mixin({bcp.class})
@Implements({@Interface(iface = ItemFrame.class, prefix = "itemFrame$", remap = Interface.Remap.NONE)})
public abstract class MixinItemFrame extends MixinEntity implements ItemFrame {
    @Shadow
    public abstract bmb shadow$o();

    @Intrinsic
    @Nullable
    public ItemStack itemFrame$getItem() {
        return shadow$o();
    }
}
