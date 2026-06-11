package net.labymod.v1_21_3.mixins.debug;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/debug/MixinDynamicTextureNullCheck.class */
@Mixin({hay.class})
public class MixinDynamicTextureNullCheck {
    @Inject(method = {"<init>(Lcom/mojang/blaze3d/platform/NativeImage;)V"}, at = {@At("TAIL")})
    private void labyMod$nullCheck(ffs image, CallbackInfo ci) {
        if (image == null) {
            throw new NullPointerException("pixels is null");
        }
    }
}
