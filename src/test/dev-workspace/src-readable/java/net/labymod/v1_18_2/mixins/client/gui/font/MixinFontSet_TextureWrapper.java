package net.labymod.v1_18_2.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.FontTextureExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/font/MixinFontSet_TextureWrapper.class */
@Mixin({ebr.class})
public class MixinFontSet_TextureWrapper {
    @WrapOperation(method = {"stitch"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;Z)Lnet/minecraft/client/gui/font/FontTexture;")})
    private ebs labyMod$addGlyph(yt textureLocation, boolean colored, Operation<ebs> original) {
        ebs fontTexture = (ebs) original.call(new Object[]{textureLocation, Boolean.valueOf(colored)});
        FontTextureExtension extension = FontTextureExtension.cast(fontTexture);
        extension.labyMod$setTextureLocation((ResourceLocation) textureLocation);
        return fontTexture;
    }
}
