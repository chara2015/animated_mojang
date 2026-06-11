package net.labymod.v1_21_10.client.renderer.state.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/renderer/state/gui/SentinelRenderState.class */
public final class SentinelRenderState implements gkm {
    public static final RenderPipeline SENTINEL_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[]{hgi.aJ}).withLocation(amj.b("labymod", "pipeline/sentinel")).withVertexFormat(fty.j, VertexFormat.b.h).withVertexShader("core/gui").withFragmentShader("core/gui").build();
    private static final int TRANSPARENT_COLOR = 16777215;
    private final gju bounds;
    private final CanvasSnapshot snapshot;

    public SentinelRenderState(gju bounds, CanvasSnapshot snapshot) {
        this.bounds = bounds;
        this.snapshot = snapshot;
    }

    public void a(fud vertexConsumer) {
        vertexConsumer.a(0.0f, 0.0f, 0.0f).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(0.0f, 1.0f, 0.0f).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(1.0f, 1.0f, 0.0f).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(1.0f, 0.0f, 0.0f).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
    }

    public RenderPipeline a() {
        return SENTINEL_PIPELINE;
    }

    public gjy b() {
        return gjy.a();
    }

    public gju m() {
        return null;
    }

    public gju n() {
        return this.bounds;
    }

    public CanvasSnapshot snapshot() {
        return this.snapshot;
    }
}
