package net.labymod.v1_21_11.mixins.mojang.blaze3d.textures;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.textures.TextureFormat;
import net.labymod.v1_21_11.client.gfx.texture.CustomTextureFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/mojang/blaze3d/textures/MixinGlConst_CustomTextureFormat.class */
@Mixin({GlConst.class})
public class MixinGlConst_CustomTextureFormat {
    @Inject(method = {"toGlInternalId"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlInternalId(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(35056);
        }
    }

    @Inject(method = {"toGlExternalId"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlExternalId(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(34041);
        }
    }

    @Inject(method = {"toGlType"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlType(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(34042);
        }
    }
}
