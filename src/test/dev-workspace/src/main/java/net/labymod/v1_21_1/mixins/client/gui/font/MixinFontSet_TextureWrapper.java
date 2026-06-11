package net.labymod.v1_21_1.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_1.client.gfx.pipeline.renderer.text.FontTextureExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/gui/font/MixinFontSet_TextureWrapper.class */
@Mixin({fld.class})
public class MixinFontSet_TextureWrapper {
    @WrapOperation(method = {"stitch"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/gui/font/GlyphRenderTypes;Z)Lnet/minecraft/client/gui/font/FontTexture;")})
    private fle labyMod$addGlyph(flf renderTypes, boolean colored, Operation<fle> original, @Local LocalRef<akr> textureLocation) {
        fle fontTexture = (fle) original.call(new Object[]{renderTypes, Boolean.valueOf(colored)});
        FontTextureExtension extension = FontTextureExtension.cast(fontTexture);
        extension.labyMod$setTextureLocation((ResourceLocation) textureLocation.get());
        return fontTexture;
    }
}
