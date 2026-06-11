package net.labymod.v1_21_5.mixins.client.renderer.texture;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/texture/MixinSkinTextureDownloader_TextureFiltering.class */
@Mixin({hkj.class})
public class MixinSkinTextureDownloader_TextureFiltering {
    @WrapOperation(method = {"lambda$registerTextureInManager$2"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V")})
    private static void labyMod$setTextureFiltering(hks instance, alr $$0, hkb $$1, Operation<Void> original) {
        original.call(new Object[]{instance, $$0, $$1});
        $$1.a(false, false);
    }
}
