package net.labymod.v1_20_4.mixins.debug;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/debug/MixinDynamicTextureNullCheck.class */
@Mixin({gea.class})
public class MixinDynamicTextureNullCheck {
    @Inject(method = {"<init>(Lcom/mojang/blaze3d/platform/NativeImage;)V"}, at = {@At("TAIL")})
    private void labyMod$nullCheck(epc image, CallbackInfo ci) {
        if (image == null) {
            throw new NullPointerException("pixels is null");
        }
    }
}
