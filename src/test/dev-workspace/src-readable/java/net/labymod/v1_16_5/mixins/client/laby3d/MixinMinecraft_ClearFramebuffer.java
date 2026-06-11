package net.labymod.v1_16_5.mixins.client.laby3d;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/laby3d/MixinMinecraft_ClearFramebuffer.class */
@Mixin({djz.class})
public class MixinMinecraft_ClearFramebuffer {

    @Shadow
    @Final
    private deg an;

    @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", shift = At.Shift.BEFORE)})
    private void labyMod$clearFramebuffer(CallbackInfo ci) {
        this.an.b(djz.a);
    }
}
