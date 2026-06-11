package net.labymod.api.client.gfx.pipeline.renderer.text.state;

import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/state/RenderedEffect.class */
public interface RenderedEffect {
    RenderState renderState();

    DeviceTextureView textureView();

    void buildVertices(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, boolean z);
}
