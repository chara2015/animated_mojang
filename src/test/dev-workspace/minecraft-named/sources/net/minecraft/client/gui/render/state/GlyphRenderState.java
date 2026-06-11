package net.minecraft.client.gui.render.state;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/state/GlyphRenderState.class */
public final class GlyphRenderState extends Record implements GuiElementRenderState {
    private final Matrix3x2fc pose;
    private final TextRenderable renderable;
    private final ScreenRectangle scissorArea;

    public GlyphRenderState(Matrix3x2fc $$0, TextRenderable $$1, ScreenRectangle $$2) {
        this.pose = $$0;
        this.renderable = $$1;
        this.scissorArea = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphRenderState.class), GlyphRenderState.class, "pose;renderable;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->renderable:Lnet/minecraft/client/gui/font/TextRenderable;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphRenderState.class), GlyphRenderState.class, "pose;renderable;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->renderable:Lnet/minecraft/client/gui/font/TextRenderable;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphRenderState.class, Object.class), GlyphRenderState.class, "pose;renderable;scissorArea", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->renderable:Lnet/minecraft/client/gui/font/TextRenderable;", "FIELD:Lnet/minecraft/client/gui/render/state/GlyphRenderState;->scissorArea:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Matrix3x2fc pose() {
        return this.pose;
    }

    public TextRenderable renderable() {
        return this.renderable;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public void buildVertices(VertexConsumer $$0) {
        this.renderable.render(new Matrix4f().mul(this.pose), $$0, LightTexture.FULL_BRIGHT, true);
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public RenderPipeline pipeline() {
        return this.renderable.guiPipeline();
    }

    @Override // net.minecraft.client.gui.render.state.GuiElementRenderState
    public TextureSetup textureSetup() {
        return TextureSetup.singleTextureWithLightmap(this.renderable.textureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
    }

    @Override // net.minecraft.client.gui.render.state.ScreenArea
    public ScreenRectangle bounds() {
        return null;
    }
}
