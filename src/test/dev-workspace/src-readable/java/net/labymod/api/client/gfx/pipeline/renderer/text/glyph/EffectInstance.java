package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance.class */
public final class EffectInstance<T extends EffectGlyph & GlyphRenderResources> extends Record implements GlyphRenderable {
    private final T glyph;
    private final float left;
    private final float top;
    private final float right;
    private final float bottom;
    private final float depth;
    private final int color;
    private final int shadowColor;
    private final float shadowOffset;
    private final EffectGlyphVertexEmitter emitter;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance$EffectGlyphVertexEmitter.class */
    @FunctionalInterface
    public interface EffectGlyphVertexEmitter {
        void emit(EffectInstance<?> effectInstance, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float f2, int i, int i2);
    }

    public EffectInstance(T glyph, float left, float top, float right, float bottom, float depth, int color, int shadowColor, float shadowOffset, EffectGlyphVertexEmitter emitter) {
        this.glyph = glyph;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.depth = depth;
        this.color = color;
        this.shadowColor = shadowColor;
        this.shadowOffset = shadowOffset;
        this.emitter = emitter;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EffectInstance.class), EffectInstance.class, "glyph;left;top;right;bottom;depth;color;shadowColor;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->left:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->top:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->right:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->bottom:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->depth:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance$EffectGlyphVertexEmitter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EffectInstance.class), EffectInstance.class, "glyph;left;top;right;bottom;depth;color;shadowColor;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->left:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->top:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->right:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->bottom:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->depth:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance$EffectGlyphVertexEmitter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EffectInstance.class, Object.class), EffectInstance.class, "glyph;left;top;right;bottom;depth;color;shadowColor;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->left:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->top:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->right:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->bottom:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->depth:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/EffectInstance$EffectGlyphVertexEmitter;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public T glyph() {
        return this.glyph;
    }

    public float left() {
        return this.left;
    }

    public float top() {
        return this.top;
    }

    public float right() {
        return this.right;
    }

    public float bottom() {
        return this.bottom;
    }

    public float depth() {
        return this.depth;
    }

    public int color() {
        return this.color;
    }

    public int shadowColor() {
        return this.shadowColor;
    }

    public float shadowOffset() {
        return this.shadowOffset;
    }

    public EffectGlyphVertexEmitter emitter() {
        return this.emitter;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public Material material(int flags) {
        return this.glyph.material(flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public RenderState guiRenderState() {
        return this.glyph.guiRenderState();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public DeviceTextureView textureView() {
        return this.glyph.textureView();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public void buildVertices(Matrix4f pose, VertexConsumer consumer, int lightCoords, boolean ignoreDepthOffset, GlyphProperties properties) {
        float depth = ignoreDepthOffset ? 0.0f : depth();
        if (hasShadow()) {
            this.emitter.emit(this, pose, consumer, shadowOffset(), depth, shadowColor(), lightCoords);
            depth += ignoreDepthOffset ? 0.0f : 0.03f;
        }
        this.emitter.emit(this, pose, consumer, 0.0f, depth, color(), lightCoords);
    }

    boolean hasShadow() {
        return shadowColor() != 0;
    }
}
