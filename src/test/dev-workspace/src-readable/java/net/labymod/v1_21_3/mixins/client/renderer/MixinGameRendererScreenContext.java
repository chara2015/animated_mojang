package net.labymod.v1_21_3.mixins.client.renderer;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/MixinGameRendererScreenContext.class */
@Mixin({glb.class})
public class MixinGameRendererScreenContext {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledHeight()I", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$disableScreenContext(flw $$0, boolean $$1, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(I)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$enableScreenContext(flw $$0, boolean $$1, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
