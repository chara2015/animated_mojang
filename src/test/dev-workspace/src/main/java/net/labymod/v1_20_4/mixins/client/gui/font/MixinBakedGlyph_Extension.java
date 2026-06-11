package net.labymod.v1_20_4.mixins.client.gui.font;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension;
import net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.GlyphInstance;
import net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.GlyphUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/gui/font/MixinBakedGlyph_Extension.class */
@Mixin({ezz.class})
public abstract class MixinBakedGlyph_Extension implements BakedGlyphExtension {

    @Shadow
    @Final
    private float i;

    @Shadow
    @Final
    private float h;

    @Shadow
    @Final
    private float g;

    @Shadow
    @Final
    private float f;
    private ResourceLocation labyMod$textureLocation;
    private boolean labyMod$colored;

    @Shadow
    public abstract void a(boolean z, float f, float f2, Matrix4f matrix4f, eqf eqfVar, float f3, float f4, float f5, float f6, int i);

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public boolean labyMod$isColored() {
        return this.labyMod$colored;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$setColored(boolean colored) {
        this.labyMod$colored = colored;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public ResourceLocation labyMod$getTextureLocation() {
        return this.labyMod$textureLocation;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$setTextureLocation(ResourceLocation location) {
        this.labyMod$textureLocation = location;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$renderGlyph(GlyphInstance instance, Matrix4f pose, eqf consumer, int lightCoords, boolean ignoreDepthOffset) {
        GlyphUtil.emitGlyph(instance, pose, consumer, lightCoords, ignoreDepthOffset, (italic, x, y, depthOffset, pose1, consumer1, argb, bold, lightCoords1) -> {
            ColorFormat format = ColorFormat.ARGB32;
            float red = format.normalizedRed(argb);
            float green = format.normalizedGreen(argb);
            float blue = format.normalizedBlue(argb);
            float alpha = format.normalizedAlpha(argb);
            a(italic, x, y, pose1, consumer1, red, green, blue, alpha, lightCoords1);
        });
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getLeft() {
        return this.f;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getRight() {
        return this.g;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getUp() {
        return this.h;
    }

    @Override // net.labymod.v1_20_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getDown() {
        return this.i;
    }
}
