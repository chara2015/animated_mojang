package net.labymod.v1_8_9.mixins.client.screen.branding;

import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/screen/branding/MixinInventoryScreen.class */
@Mixin({azc.class})
public class MixinInventoryScreen {
    @Inject(method = {"drawScreen"}, at = {@At("TAIL")})
    private void labyMod$renderBranding(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        BrandingRenderer.renderCentered(VersionedStackProvider.DEFAULT_STACK, 96.0f);
    }
}
