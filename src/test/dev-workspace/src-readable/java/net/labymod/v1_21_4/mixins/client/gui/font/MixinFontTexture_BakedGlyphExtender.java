package net.labymod.v1_21_4.mixins.client.gui.font;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension;
import net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontTextureExtension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/font/MixinFontTexture_BakedGlyphExtender.class */
@Mixin({frn.class})
public class MixinFontTexture_BakedGlyphExtender implements FontTextureExtension {

    @Shadow
    @Final
    private boolean f;
    private ResourceLocation labyMod$textureLocation;

    @WrapOperation(method = {"add"}, at = {@At(value = "NEW", target = "(Lnet/minecraft/client/gui/font/GlyphRenderTypes;FFFFFFFF)Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;")})
    private frq labyMod$addGlyph(fro renderTypes, float u0, float u1, float v0, float v1, float left, float right, float up, float down, Operation<frq> original) {
        frq bakedGlyph = (frq) original.call(new Object[]{renderTypes, Float.valueOf(u0), Float.valueOf(u1), Float.valueOf(v0), Float.valueOf(v1), Float.valueOf(left), Float.valueOf(right), Float.valueOf(up), Float.valueOf(down)});
        if (bakedGlyph != null) {
            BakedGlyphExtension extension = BakedGlyphExtension.cast(bakedGlyph);
            extension.labyMod$setTextureLocation(this.labyMod$textureLocation);
            extension.labyMod$setColored(this.f);
        }
        return bakedGlyph;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.FontTextureExtension
    public void labyMod$setTextureLocation(ResourceLocation location) {
        this.labyMod$textureLocation = location;
    }
}
