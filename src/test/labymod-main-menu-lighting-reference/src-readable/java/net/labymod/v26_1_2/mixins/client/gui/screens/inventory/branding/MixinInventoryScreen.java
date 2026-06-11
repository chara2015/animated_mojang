package net.labymod.v26_1_2.mixins.client.gui.screens.inventory.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/gui/screens/inventory/branding/MixinInventoryScreen.class */
@Mixin({InventoryScreen.class})
public class MixinInventoryScreen {
    @Inject(method = {"extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(MinecraftUtil.obtainStackFromGraphics(graphics), 96.0f);
    }
}
