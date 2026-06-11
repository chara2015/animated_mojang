package net.labymod.v1_19_4.mixins.client.gui.screens.inventory.branding;

import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/inventory/branding/MixinInventoryScreen.class */
@Mixin({eva.class})
public class MixinInventoryScreen {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", shift = At.Shift.BEFORE)})
    private void labyMod$renderBranding(ehe poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(((VanillaStackAccessor) poseStack).stack(), 96.0f);
    }
}
