package net.labymod.v1_8_9.mixins.client.laby3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/laby3d/MixinMinecraft_ClearFramebuffer.class */
@Mixin({ave.class})
public class MixinMinecraft_ClearFramebuffer {

    @Shadow
    private bfw aF;

    @Inject(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE)})
    private void labyMod$clearFramebuffer(CallbackInfo ci) {
        this.aF.f();
    }
}
