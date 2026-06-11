package net.labymod.v26_2_snapshot_8.client.renderer.state.gui;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.v26_2_snapshot_8.client.renderer.LabyRenderPipelineSnippets;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/renderer/state/gui/SentinelRenderState.class */
public final class SentinelRenderState implements GuiElementRenderState {
    public static final RenderPipeline SENTINEL_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[]{LabyRenderPipelineSnippets.MATRICES_SNIPPET}).withLocation(Identifier.tryBuild("labymod", "pipeline/sentinel")).withPrimitiveTopology(PrimitiveTopology.QUADS).withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withVertexShader("core/gui").withFragmentShader("core/gui").build();
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
