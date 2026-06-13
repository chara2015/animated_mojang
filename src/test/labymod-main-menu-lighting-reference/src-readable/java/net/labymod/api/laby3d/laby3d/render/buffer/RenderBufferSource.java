package net.labymod.api.laby3d.render.buffer;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/buffer/RenderBufferSource.class */
@Referenceable
public interface RenderBufferSource {
    VertexConsumer getBuffer(Material material);

    void endBatch();
}
