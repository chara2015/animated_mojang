package net.labymod.v1_12_2.mixins.client.screen.branding;

import net.labymod.api.Laby;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/screen/branding/MixinCreativeModeInventoryScreen.class */
@Mixin({bmp.class})
public class MixinCreativeModeInventoryScreen {
    @Inject(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/InventoryEffectRenderer;drawScreen(IIF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderBranding(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        bhz.a();
        BrandingRenderer.renderCentered(VersionedStackProvider.DEFAULT_STACK, 108.0f);
        laby3D.restoreStates();
    }
}
