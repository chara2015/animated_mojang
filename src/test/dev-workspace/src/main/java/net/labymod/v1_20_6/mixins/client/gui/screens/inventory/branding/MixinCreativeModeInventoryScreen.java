package net.labymod.v1_20_6.mixins.client.gui.screens.inventory.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/screens/inventory/branding/MixinCreativeModeInventoryScreen.class */
@Mixin({fot.class})
public class MixinCreativeModeInventoryScreen {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(fgt graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(graphics.c().stack(), 108.0f);
    }
}
