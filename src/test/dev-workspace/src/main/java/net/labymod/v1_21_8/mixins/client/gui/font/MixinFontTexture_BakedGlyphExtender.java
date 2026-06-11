package net.labymod.v1_21_8.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.v1_21_8.client.gfx.pipeline.renderer.text.BakedGlyphExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/font/MixinFontTexture_BakedGlyphExtender.class */
@Mixin({gap.class})
public class MixinFontTexture_BakedGlyphExtender {

    @Shadow
    @Final
    private boolean e;

    @WrapOperation(method = {"add"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/gui/font/GlyphRenderTypes;Lcom/mojang/blaze3d/textures/GpuTextureView;FFFFFFFF)Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;")})
    private gas labyMod$addGlyph(gaq renderTypes, GpuTextureView textureView, float u0, float u1, float v0, float v1, float left, float right, float up, float down, Operation<gas> original) {
        gas bakedGlyph = (gas) original.call(new Object[]{renderTypes, textureView, Float.valueOf(u0), Float.valueOf(u1), Float.valueOf(v0), Float.valueOf(v1), Float.valueOf(left), Float.valueOf(right), Float.valueOf(up), Float.valueOf(down)});
        if (bakedGlyph != null) {
            BakedGlyphExtension extension = BakedGlyphExtension.cast(bakedGlyph);
            extension.labyMod$setColored(this.e);
        }
        return bakedGlyph;
    }
}
