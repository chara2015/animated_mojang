package net.labymod.api.client.gfx.pipeline.renderer.text.state;

import java.util.Collections;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/state/RenderedGlyph.class */
public interface RenderedGlyph {
    RenderState renderState();

    DeviceTextureView textureView();

    void buildVertices(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, boolean z, Map<ResourceLocation, Object> map);

    default void buildVertices(VertexConsumer consumer, Matrix4f pose, int lightCoords, boolean ignoreDepthOffset) {
        buildVertices(consumer, pose, lightCoords, ignoreDepthOffset, Collections.emptyMap());
    }
}
