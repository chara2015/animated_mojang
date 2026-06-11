package net.labymod.v1_21_11.laby3d.render.buffer;

import javax.inject.Singleton;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.v1_21_11.laby3d.buffer.VersionedGameVertexConsumer;
import net.labymod.v1_21_11.laby3d.pipeline.RenderTypeResolver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/render/buffer/VersionedRenderBufferSource.class */
@Singleton
@Implements(RenderBufferSource.class)
public class VersionedRenderBufferSource implements RenderBufferSource {
    private final RenderTypeResolver resolver = new RenderTypeResolver();

    public VertexConsumer getBuffer(Material material) {
        return new VersionedGameVertexConsumer(getBufferSource().getBuffer(this.resolver.resolve(material)));
    }

    public void endBatch() {
        getBufferSource().endBatch();
    }

    private MultiBufferSource.BufferSource getBufferSource() {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    }
}
