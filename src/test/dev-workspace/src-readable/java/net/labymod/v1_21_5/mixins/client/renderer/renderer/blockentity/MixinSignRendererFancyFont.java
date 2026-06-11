package net.labymod.v1_21_5.mixins.client.renderer.renderer.blockentity;

import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/renderer/blockentity/MixinSignRendererFancyFont.class */
@Mixin({gtu.class})
public abstract class MixinSignRendererFancyFont {
    @Inject(method = {"renderSignText"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;getDarkColor(Lnet/minecraft/world/level/block/entity/SignText;)I", shift = At.Shift.AFTER)})
    private void labyMod$configureTextEdgeStrength(iw $$0, ead $$1, fld $$2, grn $$3, int $$4, int $$5, int $$6, boolean $$7, CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.0f);
    }

    @Inject(method = {"renderSignText"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE)})
    private void labyMod$setDefaultTextEdgeStrength(iw $$0, ead $$1, fld $$2, grn $$3, int $$4, int $$5, int $$6, boolean $$7, CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.5f);
    }
}
