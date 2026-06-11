package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.v1_8_9.client.gfx.pipeline.util.VersionedScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinEntityRendererScreenDraw.class */
@Mixin({bfk.class})
public class MixinEntityRendererScreenDraw {
    @Redirect(method = {"updateCameraAndRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(IIF)V"))
    private void labyMod$fireScreenRenderEvent(axu screen, int mouseX, int mouseY, float tickDelta) {
        VersionedScreenUtil.drawScreen(screen, mouseX, mouseY, tickDelta);
    }
}
