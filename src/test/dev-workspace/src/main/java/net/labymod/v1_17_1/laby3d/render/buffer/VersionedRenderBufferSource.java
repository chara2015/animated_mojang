package net.labymod.v1_17_1.laby3d.render.buffer;

import javax.inject.Singleton;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.v1_17_1.laby3d.buffer.VersionedGameVertexConsumer;
import net.labymod.v1_17_1.laby3d.pipeline.RenderTypeResolver;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/render/buffer/VersionedRenderBufferSource.class */
@Singleton
@Implements(RenderBufferSource.class)
public class VersionedRenderBufferSource implements RenderBufferSource {
    private final RenderTypeResolver resolver = new RenderTypeResolver();

    @Override // net.labymod.api.laby3d.render.buffer.RenderBufferSource
    public VertexConsumer getBuffer(Material material) {
        return new VersionedGameVertexConsumer(getBufferSource().getBuffer(this.resolver.resolve(material)));
    }

    @Override // net.labymod.api.laby3d.render.buffer.RenderBufferSource
    public void endBatch() {
        getBufferSource().b();
    }

    private a getBufferSource() {
        return dvp.C().aE().b();
    }
}
