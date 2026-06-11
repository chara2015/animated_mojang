package net.labymod.v1_16_5.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontTextureExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/font/MixinFontSet_TextureWrapper.class */
@Mixin({dmw.class})
public class MixinFontSet_TextureWrapper {
    @WrapOperation(method = {"stitch"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;Z)Lnet/minecraft/client/gui/font/FontTexture;")})
    private dmx labyMod$addGlyph(vk textureLocation, boolean colored, Operation<dmx> original) {
        dmx fontTexture = (dmx) original.call(new Object[]{textureLocation, Boolean.valueOf(colored)});
        FontTextureExtension extension = FontTextureExtension.cast(fontTexture);
        extension.labyMod$setTextureLocation((ResourceLocation) textureLocation);
        return fontTexture;
    }
}
