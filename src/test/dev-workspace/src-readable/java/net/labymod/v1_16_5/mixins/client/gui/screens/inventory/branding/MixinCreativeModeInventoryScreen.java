package net.labymod.v1_16_5.mixins.client.gui.screens.inventory.branding;

import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/inventory/branding/MixinCreativeModeInventoryScreen.class */
@Mixin({dqc.class})
public class MixinCreativeModeInventoryScreen {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(dfm poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(((VanillaStackAccessor) poseStack).stack(), 108.0f);
    }
}
