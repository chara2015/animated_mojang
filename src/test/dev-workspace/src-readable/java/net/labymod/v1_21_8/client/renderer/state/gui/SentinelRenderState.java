package net.labymod.v1_21_8.client.renderer.state.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiTransform;
import net.labymod.api.util.RenderUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/renderer/state/gui/SentinelRenderState.class */
public final class SentinelRenderState implements gcw {
    public static final RenderPipeline SENTINEL_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[]{gxx.aH}).withLocation(ame.b("labymod", "pipeline/sentinel")).withVertexFormat(fob.k, VertexFormat.b.h).withVertexShader("core/gui").withFragmentShader("core/gui").build();
    private static final int TRANSPARENT_COLOR = 16777215;
    private final gcd bounds;
    private final CanvasSnapshot snapshot;

    public SentinelRenderState(gcd bounds, CanvasSnapshot snapshot) {
        this.bounds = bounds;
        this.snapshot = snapshot;
    }

    public void a(fog vertexConsumer, float zOffset) {
        RenderUtil.setLastElementLayer(zOffset);
        vertexConsumer.a(0.0f, 0.0f, zOffset).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(0.0f, 1.0f, zOffset).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(1.0f, 1.0f, zOffset).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        vertexConsumer.a(1.0f, 0.0f, zOffset).a(0.0f, 0.0f).a(TRANSPARENT_COLOR);
        for (GuiComponent guiComponent : this.snapshot.capturedComponents()) {
            if (guiComponent instanceof GuiTransform) {
                GuiTransform guiTransform = (GuiTransform) guiComponent;
                guiTransform.pose().translate(0.0f, 0.0f, zOffset);
            }
        }
    }

    public RenderPipeline a() {
        return SENTINEL_PIPELINE;
    }

    public gch b() {
        return gch.a();
    }

    public gcd m() {
        return null;
    }

    public gcd n() {
        return this.bounds;
    }

    public CanvasSnapshot snapshot() {
        return this.snapshot;
    }
}
