package net.labymod.v26_1_2.mixins.mojang.blaze3d.textures;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.textures.TextureFormat;
import net.labymod.v26_1_2.client.gfx.texture.CustomTextureFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/mojang/blaze3d/textures/MixinGlConst_CustomTextureFormat.class */
@Mixin({GlConst.class})
public class MixinGlConst_CustomTextureFormat {
    @Inject(method = {"toGlInternalId"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlInternalId(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(Integer.valueOf(net.labymod.api.client.gfx.GlConst.GL_DEPTH24_STENCIL8));
        }
    }

    @Inject(method = {"toGlExternalId"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlExternalId(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(Integer.valueOf(net.labymod.api.client.gfx.GlConst.GL_DEPTH_STENCIL));
        }
    }

    @Inject(method = {"toGlType"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$toGlType(TextureFormat format, CallbackInfoReturnable<Integer> cir) {
        if (format == CustomTextureFormat.DEPTH24_STENCIL8) {
            cir.setReturnValue(Integer.valueOf(net.labymod.api.client.gfx.GlConst.GL_UNSIGNED_INT_24_8));
        }
    }
}
