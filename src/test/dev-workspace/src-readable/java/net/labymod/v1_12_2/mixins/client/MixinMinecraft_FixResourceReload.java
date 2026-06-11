package net.labymod.v1_12_2.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinMinecraft_FixResourceReload.class */
@Mixin({bib.class})
public abstract class MixinMinecraft_FixResourceReload {
    @Shadow
    public abstract void f();

    @WrapOperation(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;refreshResources()V")})
    private void labyMod$refreshResources(bib instance, Operation<Void> original) {
    }

    @WrapOperation(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;drawSplashScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V")})
    private void labyMod$drawSplashScreen(bib instance, cdr lvt_1_1_, Operation<Void> original) {
        f();
        original.call(new Object[]{instance, lvt_1_1_});
    }
}
