package net.labymod.v26_2_snapshot_8.mixins.client.gui.screens.inventory.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/screens/inventory/branding/MixinCreativeModeInventoryScreen.class */
@Mixin({CreativeModeInventoryScreen.class})
public class MixinCreativeModeInventoryScreen {
    @Inject(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;setTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;II)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(MinecraftUtil.obtainStackFromGraphics(graphics), 108.0f);
    }
}
