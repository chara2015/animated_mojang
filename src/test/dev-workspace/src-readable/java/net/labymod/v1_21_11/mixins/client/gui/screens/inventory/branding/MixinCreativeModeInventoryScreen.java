package net.labymod.v1_21_11.mixins.client.gui.screens.inventory.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/screens/inventory/branding/MixinCreativeModeInventoryScreen.class */
@Mixin({CreativeModeInventoryScreen.class})
public class MixinCreativeModeInventoryScreen {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(MinecraftUtil.obtainStackFromGraphics(graphics), 108.0f);
    }
}
