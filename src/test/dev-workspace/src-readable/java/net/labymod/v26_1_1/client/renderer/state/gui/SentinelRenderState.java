package net.labymod.v26_1_1.client.renderer.state.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/renderer/state/gui/SentinelRenderState.class */
public final class SentinelRenderState implements GuiElementRenderState {
    public static final RenderPipeline SENTINEL_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[]{RenderPipelines.MATRICES_PROJECTION_SNIPPET}).withLocation(Identifier.tryBuild("labymod", "pipeline/sentinel")).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS).withVertexShader("core/gui").withFragmentShader("core/gui").build();
    private static final int TRANSPARENT_COLOR = 16777215;
    private final ScreenRectangle bounds;
    private final CanvasSnapshot snapshot;

    public SentinelRenderState(CanvasSnapshot snapshot) {
        this(new ScreenRectangle(0, 0, 1, 1), snapshot);
    }

    public SentinelRenderState(ScreenRectangle bounds, CanvasSnapshot snapshot) {
        this.bounds = bounds;
        this.snapshot = snapshot;
    }

    public void buildVertices(VertexConsumer vertexConsumer) {
        vertexConsumer.addVertex(0.0f, 0.0f, 0.0f).setUv(0.0f, 0.0f).setColor(TRANSPARENT_COLOR);
        vertexConsumer.addVertex(0.0f, 1.0f, 0.0f).setUv(0.0f, 0.0f).setColor(TRANSPARENT_COLOR);
        vertexConsumer.addVertex(1.0f, 1.0f, 0.0f).setUv(0.0f, 0.0f).setColor(TRANSPARENT_COLOR);
        vertexConsumer.addVertex(1.0f, 0.0f, 0.0f).setUv(0.0f, 0.0f).setColor(TRANSPARENT_COLOR);
    }

    public RenderPipeline pipeline() {
        return SENTINEL_PIPELINE;
    }

    public TextureSetup textureSetup() {
        return TextureSetup.noTexture();
    }

    public ScreenRectangle scissorArea() {
        return null;
    }

    public ScreenRectangle bounds() {
        return this.bounds;
    }

    public CanvasSnapshot snapshot() {
        return this.snapshot;
    }
}
