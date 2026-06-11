package net.labymod.v1_21_3.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_3.client.gfx.pipeline.renderer.text.FontTextureExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/gui/font/MixinFontSet_TextureWrapper.class */
@Mixin({fqy.class})
public class MixinFontSet_TextureWrapper {
    @WrapOperation(method = {"stitch"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/gui/font/GlyphRenderTypes;Z)Lnet/minecraft/client/gui/font/FontTexture;")})
    private fqz labyMod$addGlyph(fra renderTypes, boolean colored, Operation<fqz> original, @Local LocalRef<alz> textureLocation) {
        fqz fontTexture = (fqz) original.call(new Object[]{renderTypes, Boolean.valueOf(colored)});
        FontTextureExtension extension = FontTextureExtension.cast(fontTexture);
        extension.labyMod$setTextureLocation((ResourceLocation) textureLocation.get());
        return fontTexture;
    }
}
