package net.labymod.v26_2_snapshot_8.mixins.client.entity.decoration;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemUtil;
import net.labymod.v26_2_snapshot_8.mixins.client.entity.MixinEntity;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/entity/decoration/MixinItemFrame.class */
@Mixin({ItemFrame.class})
@Implements({@Interface(iface = net.labymod.api.client.entity.decoration.ItemFrame.class, prefix = "itemFrame$", remap = Interface.Remap.NONE)})
public abstract class MixinItemFrame extends MixinEntity implements net.labymod.api.client.entity.decoration.ItemFrame {
    @Shadow
    public abstract ItemStack shadow$getItem();

    @Intrinsic
    @Nullable
    public net.labymod.api.client.world.item.ItemStack itemFrame$getItem() {
        return shadow$getItem();
    }

    @Override // net.labymod.api.client.entity.decoration.ItemFrame
    public ResourceLocation getFrameType() {
        return ItemUtil.getFrameType(this instanceof GlowItemFrame);
    }
}
