package net.labymod.v1_21_11.mixins.client.gui.screens.inventory.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/screens/inventory/branding/MixinInventoryScreen.class */
@Mixin({gul.class})
public class MixinInventoryScreen {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(gir graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(MinecraftUtil.obtainStackFromGraphics(graphics), 96.0f);
    }
}
