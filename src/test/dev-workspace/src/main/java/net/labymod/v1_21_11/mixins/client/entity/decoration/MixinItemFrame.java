package net.labymod.v1_21_11.mixins.client.entity.decoration;

import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemUtil;
import net.labymod.v1_21_11.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/entity/decoration/MixinItemFrame.class */
@Mixin({czc.class})
@Implements({@Interface(iface = ItemFrame.class, prefix = "itemFrame$", remap = Interface.Remap.NONE)})
public abstract class MixinItemFrame extends MixinEntity implements ItemFrame {
    @Shadow
    public abstract dlt shadow$w();

    @Intrinsic
    @Nullable
    public ItemStack itemFrame$getItem() {
        return shadow$w();
    }

    @Override // net.labymod.api.client.entity.decoration.ItemFrame
    public ResourceLocation getFrameType() {
        return ItemUtil.getFrameType(this instanceof cza);
    }
}
