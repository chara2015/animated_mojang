package net.minecraft.client.gui.font;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/GlyphRenderTypes.class */
public final class GlyphRenderTypes extends Record {
    private final RenderType normal;
    private final RenderType seeThrough;
    private final RenderType polygonOffset;
    private final RenderPipeline guiPipeline;

    public GlyphRenderTypes(RenderType $$0, RenderType $$1, RenderType $$2, RenderPipeline $$3) {
        this.normal = $$0;
        this.seeThrough = $$1;
        this.polygonOffset = $$2;
        this.guiPipeline = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphRenderTypes.class), GlyphRenderTypes.class, "normal;seeThrough;polygonOffset;guiPipeline", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->normal:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->seeThrough:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->polygonOffset:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->guiPipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphRenderTypes.class), GlyphRenderTypes.class, "normal;seeThrough;polygonOffset;guiPipeline", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->normal:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->seeThrough:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->polygonOffset:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->guiPipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphRenderTypes.class, Object.class), GlyphRenderTypes.class, "normal;seeThrough;polygonOffset;guiPipeline", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->normal:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->seeThrough:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->polygonOffset:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/gui/font/GlyphRenderTypes;->guiPipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public RenderType normal() {
        return this.normal;
    }

    public RenderType seeThrough() {
        return this.seeThrough;
    }

    public RenderType polygonOffset() {
        return this.polygonOffset;
    }

    public RenderPipeline guiPipeline() {
        return this.guiPipeline;
    }

    public static GlyphRenderTypes createForIntensityTexture(Identifier $$0) {
        return new GlyphRenderTypes(RenderTypes.textIntensity($$0), RenderTypes.textIntensitySeeThrough($$0), RenderTypes.textIntensityPolygonOffset($$0), RenderPipelines.GUI_TEXT_INTENSITY);
    }

    public static GlyphRenderTypes createForColorTexture(Identifier $$0) {
        return new GlyphRenderTypes(RenderTypes.text($$0), RenderTypes.textSeeThrough($$0), RenderTypes.textPolygonOffset($$0), RenderPipelines.GUI_TEXT);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public RenderType select(Font.DisplayMode $$0) throws MatchException {
        switch ($$0) {
            case NORMAL:
                return this.normal;
            case SEE_THROUGH:
                return this.seeThrough;
            case POLYGON_OFFSET:
                return this.polygonOffset;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
