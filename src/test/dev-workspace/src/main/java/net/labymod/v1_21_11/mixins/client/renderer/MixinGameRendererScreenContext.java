package net.labymod.v1_21_11.mixins.client.renderer;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinGameRendererScreenContext.class */
@Mixin({hob.class})
public class MixinGameRendererScreenContext {
    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;getScaledXPos(Lcom/mojang/blaze3d/platform/Window;)D", shift = At.Shift.BEFORE)})
    private void labyMod$disableScreenContext(gez $$0, boolean $$1, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;clearDepthTexture(Lcom/mojang/blaze3d/textures/GpuTexture;D)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$enableScreenContext(gez $$0, boolean $$1, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
