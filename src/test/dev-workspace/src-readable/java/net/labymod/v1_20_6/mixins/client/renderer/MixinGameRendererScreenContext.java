package net.labymod.v1_20_6.mixins.client.renderer;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinGameRendererScreenContext.class */
@Mixin({gdj.class})
public class MixinGameRendererScreenContext {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledHeight()I", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$disableScreenContext(float param0, long param1, boolean param2, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$enableScreenContext(float param0, long param1, boolean param2, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
