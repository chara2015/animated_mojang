package net.labymod.v1_16_5.mixins.client.renderer.renderer.blockentity;

import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/renderer/blockentity/MixinSignRendererFancyFont.class */
@Mixin({ecn.class})
public abstract class MixinSignRendererFancyFont {
    @Inject(method = {"render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/item/DyeColor;getTextColor()I", shift = At.Shift.AFTER)})
    private void labyMod$configureTextEdgeStrength(cdf entity, float param1, dfm param2, eag param3, int param4, int param5, CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.0f);
    }

    @Inject(method = {"render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE, ordinal = 1)})
    private void labyMod$setDefaultTextEdgeStrength(cdf entity, float param1, dfm param2, eag param3, int param4, int param5, CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.5f);
    }
}
