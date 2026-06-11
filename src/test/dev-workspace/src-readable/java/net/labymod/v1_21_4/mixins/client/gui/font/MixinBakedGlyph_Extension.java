package net.labymod.v1_21_4.mixins.client.gui.font;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension;
import net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.GlyphUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/font/MixinBakedGlyph_Extension.class */
@Mixin({frq.class})
public abstract class MixinBakedGlyph_Extension implements BakedGlyphExtension {

    @Shadow
    @Final
    private float g;

    @Shadow
    @Final
    private float h;

    @Shadow
    @Final
    private float i;

    @Shadow
    @Final
    private float j;
    private ResourceLocation labyMod$textureLocation;
    private boolean labyMod$colored;

    @Shadow
    protected abstract void a(boolean z, float f, float f2, float f3, Matrix4f matrix4f, ffz ffzVar, int i, boolean z2, int i2);

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public ResourceLocation labyMod$getTextureLocation() {
        return this.labyMod$textureLocation;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$setTextureLocation(ResourceLocation location) {
        this.labyMod$textureLocation = location;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public boolean labyMod$isColored() {
        return this.labyMod$colored;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$setColored(boolean colored) {
        this.labyMod$colored = colored;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public void labyMod$renderGlyph(b instance, Matrix4f pose, ffz consumer, int lightCoords, boolean ignoreDepthOffset) {
        GlyphUtil.emitGlyph(instance, pose, consumer, lightCoords, ignoreDepthOffset, this::a);
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getLeft() {
        return this.g;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getRight() {
        return this.h;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getUp() {
        return this.i;
    }

    @Override // net.labymod.v1_21_4.client.gfx.pipeline.renderer.text.BakedGlyphExtension
    public float labyMod$getDown() {
        return this.j;
    }
}
