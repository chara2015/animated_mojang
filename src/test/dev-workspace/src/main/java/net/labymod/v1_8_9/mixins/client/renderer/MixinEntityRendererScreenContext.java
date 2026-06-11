package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinEntityRendererScreenContext.class */
@Mixin({bfk.class})
public class MixinEntityRendererScreenContext {
    @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER)})
    private void labyMod$disableScreenContext(float p_updateCameraAndRender_1_, long p_updateCameraAndRender_2_, CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }

    @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", shift = At.Shift.AFTER)})
    private void labyMod$enableScreenContext(float p_updateCameraAndRender_1_, long p_updateCameraAndRender_2_, CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
