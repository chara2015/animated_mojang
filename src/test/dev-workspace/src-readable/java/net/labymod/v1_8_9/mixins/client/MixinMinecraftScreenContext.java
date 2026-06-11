package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinMinecraftScreenContext.class */
@Mixin({ave.class})
public class MixinMinecraftScreenContext {
    @Inject(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableTexture2D()V", shift = At.Shift.AFTER)})
    private void labyMod$enableScreenContext(CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }

    @Inject(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;unbindFramebuffer()V", shift = At.Shift.BEFORE)})
    private void labyMod$disableScreenContext(CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
